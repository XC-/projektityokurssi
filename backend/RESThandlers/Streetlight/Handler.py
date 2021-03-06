# -*- coding: utf-8 -*-
"""
.. module:: Handlers.Streetlight.handler
    :platform: Unix, Windows
.. moduleauthor:: Aki Mäkinen <aki.makinen@outlook.com>

"""
__author__ = 'Aki Mäkinen'

import re
import urllib
import json

from RESThandlers.HandlerInterface.Exceptions import GenericDBError, ObjectNotFound
from RESThandlers.HandlerInterface.HandlerBaseClass import HandlerBase
from RESThandlers.Streetlight.models import Streetlights
from lbd_backend.utils import flattener


class StreetlightHandler(HandlerBase):
    @property
    @staticmethod
    def handler_id(self):
        return json.dumps({
            "identifier_field_name": "feature_id"
        })

    _doc_structure = {
        "_id": 0,
        "type": 1,
        "id": "$feature_id",
        "geometry": {
            "type": 1,
            "coordinates": 1
        },
        "geometry_name": 1,
        "properties": {
            "KATUVALO_ID": 1,
            "NIMI": 1,
            "TYYPPI_KOODI": 1,
            "LAMPPU_TYYPPI_KOODI": 1,
            "LAMPPU_TYYPPI": 1
        }
    }

    _doc_structure_mini = {
        "_id": 0,
        "type": 1,
        "id": "$feature_id",
        "geometry": {
            "type": 1,
            "coordinates": 1
        },
    }

    _featurecollection = {
        "type": "FeatureCollection",
        "totalFeatures": None,
        "features": list()
    }

    def __init__(self):
        self.modelobject = Streetlights

    def update_db(self):
        req = urllib.urlopen(
            'http://tampere.navici.com/tampere_wfs_geoserver/opendata/ows?service=WFS&version=1.0.0&request=GetFeature'
            '&typeName=opendata:WFS_KATUVALO&outputFormat=json&srsName=EPSG:4326',
            proxies={})
        try:
            jsonitem = json.loads(req.read())
        except ValueError:
            return False
        self.modelobject.drop_collection()
        doclist = []
        for d in jsonitem["features"]:
            id = d.pop("id")
            d["feature_id"] = id
            temp = self.modelobject(**d)
            #temp.save()
            doclist.append(temp)
            if len(doclist) % 2000 == 0:
                self.modelobject.objects.insert(doclist)
                doclist = []
        if len(doclist) > 0:
            self.modelobject.objects.insert(doclist)

        return True

    def get_by_id(self, iid):
        result = self.modelobject._get_collection().aggregate([
            {"$match": {"feature_id": iid}},
            {"$project": self._doc_structure}
        ])
        if int(result["ok"]) == 1 and len(result["result"]) > 0:
            return result["result"][0]
        else:
            raise ObjectNotFound("Object not found")


    def get_near(self, longitude, latitude, nrange=0.001, mini=False):
        if mini:
            doc_structure = self._doc_structure_mini
        else:
            doc_structure = self._doc_structure

        result = self.modelobject._get_collection().aggregate([
            {"$match":
                 {"geometry":
                      {"$geoWithin":
                           {"$center": [[float(longitude), float(latitude)], nrange]}
                      }
                 }
            },
            {"$project": doc_structure}
        ])
        itemcount = len(result["result"])
        if int(result["ok"]):
            fc = self._featurecollection
            fc["totalFeatures"] = itemcount
            fc["features"] = result["result"]

            return fc
        else:
            raise GenericDBError("Database query failed. Status: " + str(result["ok"]))

    def get_within_rectangle(self, xtop_right, ytop_right, xbottom_left, ybottom_left, mini=False):

        if mini:
            doc_structure = self._doc_structure_mini
        else:
            doc_structure = self._doc_structure

        raw = self.modelobject._get_collection().aggregate([{'$match':
                                                                 {'geometry':
                                                                      {'$geoWithin':
                                                                           {'$box': [[xbottom_left, ybottom_left],
                                                                                     [xtop_right, ytop_right]]}
                                                                      }
                                                                 }
                                                            },
                                                            {'$project': doc_structure}
        ])
        if int(raw["ok"]) != 1:
            raise GenericDBError("Database query failed. Status: " + str(raw["ok"]))
        else:
            f_count = self.modelobject.objects(geometry__geo_within_box=
                                               [(xbottom_left, ybottom_left), (xtop_right, ytop_right)]).count()
            featurecollection = self._featurecollection
            featurecollection["totalFeatures"] = f_count
            featurecollection["features"] = raw["result"]

            return featurecollection

    def get_all(self, mini=True):
        if mini:
            doc_structure = self._doc_structure_mini
        else:
            doc_structure = self._doc_structure

        raw = self.modelobject._get_collection().aggregate([{'$project': doc_structure}])
        if int(raw["ok"]) != 1:
            raise GenericDBError("Database query failed. Status: " + str(raw["ok"]))
        else:
            res_count = len(raw["result"])

            featurecollection = self._featurecollection
            featurecollection["totalFeatures"] = res_count
            featurecollection["features"] = raw["result"]

            return featurecollection


    # Return values:
    # Boolean: True if all were deleted, False if objects remain in db after deletion
    def delete_all(self):
        obj = self.modelobject.objects()
        try:
            deleted = obj.delete()
        except Exception as e:
            raise GenericDBError(e.message)


    def delete_near(self, latitude, longitude, nrange):
        itemcount = self.modelobject.objects(geometry__geo_within_center=
                                             [(float(latitude), float(longitude)), nrange]).count()

        delcount = self.modelobject.objects(geometry__geo_within_center=
                                            [(float(latitude), float(longitude)), nrange]).delete()

        if itemcount == delcount:
            return True
        else:
            return False

    # Function get_item_count
    # Return values:
    #       Integer: number of items in table
    def get_item_count(self):
        return self.modelobject.objects().count()


    def search(self, regex, limit, field=None):
        if field is None:
            raise NotImplementedError # TODO: Search from all fields... some day
        elif field == "id":
            try:
                reg = re.compile(regex, re.IGNORECASE)
            except Exception as e:
                raise ValueError(e.message)
            raw = self.modelobject._get_collection().aggregate([{'$match': {
                                                                     "feature_id": {
                                                                         "$regex": reg
                                                                     }
                                                                 }
                                                            }, {
                                                                '$project': self._doc_structure
                                                            }])
            if int(raw["ok"]) == 1:
                results = raw["result"]
            else:
                results = []
            totalresults = len(results)
            if totalresults > limit:
                results = results[:limit]
            featurecollection = self._featurecollection
            featurecollection["totalFeatures"] = len(results)
            featurecollection["features"] = results
            return totalresults, featurecollection
        else:
            raise NotImplementedError

    def get_field_names(self):
        doc = self.modelobject._get_collection().find_one()
        if doc is None:
            fields = []
        else:
            fields = []
            for item in flattener(doc, None):
                if item == "feature_id":
                    fields.append("id")
                elif item == "_id":
                    pass
                else:
                    fields.append(item)
        return fields
