import json
from unittest import TestCase
import urllib
from lbd_backend.utils import geo_json_scheme_validation
__author__ = 'xc-'


class TestGeo_json_scheme_validation(TestCase):

    def __init__(self, *args, **kwargs):
        super(TestGeo_json_scheme_validation, self).__init__(*args, **kwargs)

        # valid GeoJSON Feature
        jsonstring = '{"geometry": {"type": "Point", "coordinates": [23.643239226767022, 61.519112683582854]}, "id": ' \
                     '"WFS_KATUVALO.405172", "type": "Feature", "properties": {"NIMI": "XPWR_6769212", ' \
                     '"LAMPPU_TYYPPI_KOODI": "100340", "TYYPPI_KOODI": "105007", "KATUVALO_ID": 405172, ' \
                     '"LAMPPU_TYYPPI": "ST 100 (SIEMENS)", "metadata": {"status": "SNAFU", "modifier": ' \
                     '"Seppo S\\u00e4hk\\u00e4ri", "modified": 1414663769}}, "geometry_name": "GEOLOC"}'
        self.jsondata = json.loads(jsonstring)
        # List of types to test
        self.typetestlist = [1, 1.0, unicode("Foo"), str("Bar"), {"a":"b"}, ("a","b"), ["a", "b"]]

    def test_geo_json_scheme_validation_valid(self):
        print "Running test: Valid GeoJSON Feature"
        self.assertTrue(geo_json_scheme_validation(self.jsondata), "Validation failed!")
        print "Test passed!"

    def test_undefined_type_value(self):
        print "Running test: Undefined value of 'type'"
        temp = dict(self.jsondata)
        temp["type"] = unicode("FOOBAR")
        self.assertFalse(geo_json_scheme_validation(temp), "Validation succeeded with incorrect value!")
        print "Test passed!"

    def test_undefined_type_value_empty(self):
        print "Running test: Undefined value of 'type' (empty)"
        temp = dict(self.jsondata)
        temp["type"] = unicode("")
        print geo_json_scheme_validation(temp)
        self.assertFalse(geo_json_scheme_validation(temp), "Validation succeeded with incorrect value!")
        print "Test passed!"

    def test_missing_type(self):
        print "Running test: Missing 'type' field"
        temp = dict(self.jsondata)
        del temp["type"]
        self.assertFalse(geo_json_scheme_validation(temp), "Validation succeeded without required field!")
        print "Test passed!"

    def test_wrong_type(self):
        print "Running test: 'type' is wrong type (int, float etc)"
        skip = unicode
        results = {}
        to_fail = False
        for item in self.typetestlist:
            if type(item) is not skip:
                temp = dict(self.jsondata)
                temp["type"] = item
                temp_result = geo_json_scheme_validation(temp)
                results[str(type(item))] = temp_result
                if temp_result == True:
                    to_fail = True
        print "Results:"
        for r,v in results.iteritems():
            print str(r)+":"+str(v)
        if to_fail:
            self.fail("Validation succeeded with wrong element type")
        print "Test passed!"


    def test_missing_geometry(self):
        print "Running test: Missing 'geometry' field"
        temp = dict(self.jsondata)
        del temp["geometry"]
        self.assertFalse(geo_json_scheme_validation(temp), "Validation succeeded without a required field!")
        print "Test passed!"

    def test_undefined_geometry_type_value(self):
        print "Running test: Undefined value of 'type' in 'geometry'"
        temp = dict(self.jsondata)
        temp["geometry"]["type"] = unicode("FOOBAR")
        self.assertFalse(geo_json_scheme_validation(temp), "Validation succeeded with incorrect value!")
        print "Test passed!"

    def test_undefined_geometry_type_value_empty(self):
        print "Running test: Undefined value of 'type' in 'geometry' (empty)"
        temp = dict(self.jsondata)
        temp["geometry"]["type"] = unicode("")
        self.assertFalse(geo_json_scheme_validation(temp), "Validation succeeded with incorrect value!")
        print "Test passed!"

    def test_missing_geometry_type(self):
        print "Running test: Missing 'type' from 'geometry'"
        temp = dict(self.jsondata)
        del temp["geometry"]["type"]
        self.assertFalse(geo_json_scheme_validation(temp), "Validation succeeded without a required field!")
        print "Test passed!"

    def test_wrong_geometry_type(self):
        print "Running test: field 'geometry' is wrong type"
        skip = unicode
        results = {}
        to_fail = False
        for item in self.typetestlist:
            if type(item) is not skip:
                temp = dict(self.jsondata)
                temp["geometry"]["type"] = item
                temp_result = geo_json_scheme_validation(temp)
                results[str(type(item))] = temp_result
                if temp_result == True:
                    to_fail = True
        print "Results:"
        for r,v in results.iteritems():
            print str(r)+":"+str(v)
        if to_fail:
            self.fail()
        print "Test passed!"

    def test_missing_geometry_coordinates(self):
        print "Running test: Missing 'coordinate' field"
        temp = dict(self.jsondata)
        del temp["geometry"]["coordinates"]
        self.assertFalse(geo_json_scheme_validation(temp), "Validation succeeded without a required field!")
        print "Test passed!"

    def test_wrong_geometry_coordinates_not_list(self):
        print "Running test: Wrong 'coordinates' type"
        skip = list
        results = {}
        to_fail = False
        for item in self.typetestlist:
            if type(item) is not skip:
                temp = dict(self.jsondata)
                temp["geometry"]["coordinates"] = item
                try:
                    temp_result = geo_json_scheme_validation(temp)
                except Exception as e:
                    temp_result = e.message
                results[str(type(item))] = temp_result
                if temp_result == True:
                    to_fail = True
        print "Results:"
        for r,v in results.iteritems():
            print str(r)+":"+str(v)
        if to_fail:
            self.fail()
        print "Test passed!"

    def test_wrong_geometry_coordinates_0(self):
        print "Running test: First coordinate has wrong type"
        skip = float
        results = {}
        to_fail = False
        for item in self.typetestlist:
            if type(item) is not skip:
                temp = dict(self.jsondata)
                temp["geometry"]["coordinates"][0] = item
                temp_result = geo_json_scheme_validation(temp)
                results[str(type(item))] = temp_result
                if temp_result == True:
                    to_fail = True
        print "Results:"
        for r,v in results.iteritems():
            print str(r)+":"+str(v)
        if to_fail:
            self.fail()
        print "Test passed!"

    def test_wrong_geometry_coordinates_1(self):
        print "Running test: Second coordinate has wrong type"
        skip = float
        results = {}
        to_fail = False
        for item in self.typetestlist:
            if type(item) is not skip:
                temp = dict(self.jsondata)
                temp["geometry"]["coordinates"][1] = item
                temp_result = geo_json_scheme_validation(temp)
                results[str(type(item))] = temp_result
                if temp_result == True:
                    to_fail = True
        print "Results:"
        for r,v in results.iteritems():
            print str(r)+":"+str(v)
        if to_fail:
            self.fail()
        print "Test passed!"

    def test_wrong_amount_of_coords_too_many(self):
        print "Running test: Too many elements in coordinates list"
        temp = dict(self.jsondata)
        temp["geometry"]["coordinates"].append(2.9)
        self.assertFalse(geo_json_scheme_validation(temp))
        print "Test passed!"

    def test_wrong_amount_of_coords_too_few(self):
        print "Running test: Too few elements in coordinates list"
        temp = dict(self.jsondata)
        temp["geometry"]["coordinates"].pop()
        self.assertFalse(geo_json_scheme_validation(temp))
        print "Test passed!"

    def test_wrong_amount_of_coords_too_many_wrong_type(self):
        print "Running test: Too many elements in coordinates field, extra element wrong type"
        skip = float
        results = {}
        to_fail = False
        for item in self.typetestlist:
            if type(item) is not skip:
                temp = dict(self.jsondata)
                temp["geometry"]["coordinates"].append(item)
                temp_result = geo_json_scheme_validation(temp)
                results[str(type(item))] = temp_result
                if temp_result == True:
                    to_fail = True
        print "Results:"
        for r,v in results.iteritems():
            print str(r)+":"+str(v)
        if to_fail:
            self.fail()
        print "Test passed!"

    def test_missing_id(self):
        print "Running test: Missing 'id' field"
        temp = dict(self.jsondata)
        del temp["properties"]
        self.assertFalse(geo_json_scheme_validation(temp), "Validation succeeded without a required field!")
        print "Test passed!"

    def test_wrong_id(self):
        print "Running test: Wrong 'id' type"
        skip = unicode
        results = {}
        to_fail = False
        for item in self.typetestlist:
            if type(item) is not skip:
                temp = dict(self.jsondata)
                temp["id"] = item
                try:
                    temp_result = geo_json_scheme_validation(temp)
                except Exception as e:
                    temp_result = e.message
                    pass
                results[str(type(item))] = temp_result
                if temp_result == True:
                    to_fail = True
        print "Results:"
        for r,v in results.iteritems():
            print str(r)+":"+str(v)
        if to_fail:
            self.fail()
        print "Test passed!"

    def test_missing_properties(self):
        print "Running test: Missing 'properties' field"
        temp = dict(self.jsondata)
        del temp["properties"]
        self.assertFalse(geo_json_scheme_validation(temp), "Validation succeeded without a required field!")
        print "Test passed!"

    def test_missing_properties_metadata(self):
        print "Running test: Missing 'metadata' from 'properties'"
        temp = dict(self.jsondata)
        del temp["properties"]["metadata"]
        self.assertTrue(geo_json_scheme_validation(temp), "Validataion failed without an optional field!")
        print "Test passed!"

    def test_missing_properties_metadata_status(self):
        print "Running test: missing 'status' from 'metadata'"
        temp = dict(self.jsondata)
        del temp["properties"]["metadata"]["status"]
        self.assertFalse(geo_json_scheme_validation(temp), "Validation succeeded without a required field!")
        print "Test passed!"

    def test_wrong_properties_metadata_status(self):
        print "Running test: Wrong 'status' type"
        skip = unicode
        results = {}
        to_fail = False
        for item in self.typetestlist:
            if type(item) is not skip:
                temp = dict(self.jsondata)
                temp["properties"]["metadata"]["status"] = item
                temp_result = geo_json_scheme_validation(temp)
                results[str(type(item))] = temp_result
                if temp_result == True:
                    to_fail = True
        print "Results:"
        for r,v in results.iteritems():
            print str(r)+":"+str(v)
        if to_fail:
            self.fail()
        print "Test passed!"