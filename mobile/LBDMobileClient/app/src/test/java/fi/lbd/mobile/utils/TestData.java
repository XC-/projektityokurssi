package fi.lbd.mobile.utils;

/**
 * Created by Tommi.
 */
public class TestData {
    public static final String testJson = "{\"totalFeatures\": 2, \"type\": \"FeatureCollection\", \"features\": [{\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.64941278370676, 61.5192743640121]}, \"id\": \"WFS_KATUVALO.391441\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_4703229\", \"TYYPPI_KOODI\": \"105030\", \"KATUVALO_ID\": 391441, \"LAMPPU_TYYPPI\": \"8512 (IDMAN)\", \"LAMPPU_TYYPPI_KOODI\": \"100105\"}, \"geometry_name\": \"GEOLOC\"}, {\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.64941278370676, 61.5192743640121]}, \"id\": \"WFS_KATUVALO.391440\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_4703228\", \"TYYPPI_KOODI\": \"105030\", \"KATUVALO_ID\": 391440, \"LAMPPU_TYYPPI\": \"8512 (IDMAN)\", \"LAMPPU_TYYPPI_KOODI\": \"100105\"}, \"geometry_name\": \"GEOLOC\"}]}";
    public static final String testInvalidJson1 = "{\"totalFeatures\": 2, \"type\": \"FeatureCollection\", \"features\": [{\"geometry\": {\"type\": \"Point\", \"id\": \"WFS_KATUVALO.391441\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_4703229\", \"TYYPPI_KOODI\": \"105030\", \"KATUVALO_ID\": 391441, \"LAMPPU_TYYPPI\": \"8512 (IDMAN)\", \"LAMPPU_TYYPPI_KOODI\": \"100105\"}, \"geometry_name\": \"GEOLOC\"}, {\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.64941278370676, 61.5192743640121]}, \"id\": \"WFS_KATUVALO.391440\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_4703228\", \"TYYPPI_KOODI\": \"105030\", \"KATUVALO_ID\": 391440, \"LAMPPU_TYYPPI\": \"8512 (IDMAN)\", \"LAMPPU_TYYPPI_KOODI\": \"100105\"}, \"geometry_name\": \"GEOLOC\"}]}";
    public static final String testInvalidJson2 = "{\"totalFeatures\": 2, \"type\": \"FeatureCollection\", \"features\": [{\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.64941278370676, 61.5192743640121]}, \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_4703229\", \"TYYPPI_KOODI\": \"105030\", \"KATUVALO_ID\": 391441, \"LAMPPU_TYYPPI\": \"8512 (IDMAN)\", \"LAMPPU_TYYPPI_KOODI\": \"100105\"}, \"geometry_name\": \"GEOLOC\"}, {\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.64941278370676, 61.5192743640121]}, \"id\": \"WFS_KATUVALO.391440\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_4703228\", \"TYYPPI_KOODI\": \"105030\", \"KATUVALO_ID\": 391440, \"LAMPPU_TYYPPI\": \"8512 (IDMAN)\", \"LAMPPU_TYYPPI_KOODI\": \"100105\"}, \"geometry_name\": \"GEOLOC\"}]}";

    public static final String testJsonMini = "{\"totalFeatures\": 2, \"type\": \"FeatureCollection\", \"features\": [{\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.64941278370676, 61.5192743640121]}, \"type\": \"Feature\", \"id\": \"WFS_KATUVALO.391441\"}, {\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.64941278370676, 61.5192743640121]}, \"type\": \"Feature\", \"id\": \"WFS_KATUVALO.391440\"}]}";
    public static final String testSingleJson = "{\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.643239226767022, 61.519112683582854]}, \"id\": \"WFS_KATUVALO.405172\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_6769212\", \"TYYPPI_KOODI\": \"105007\", \"KATUVALO_ID\": 405172, \"LAMPPU_TYYPPI\": \"ST 100 (SIEMENS)\", \"LAMPPU_TYYPPI_KOODI\": \"100340\"}, \"geometry_name\": \"GEOLOC\"}";
    public static final String testInvalidSingleJson1 = "{\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.643239226767022, 61.519112683582854]}, \"id\": \"WFS_KATUVALO.405172\", \"type\": \"Feature\"s\": {\"NIMI\": \"XPWR_6769212\", \"TYYPPI_KOODI\": \"105007\", \"KATUVALO_ID\": 405172, \"LAMPPU_TYYPPI\": \"ST 100 (SIEMENS)\", \"LAMPPU_TYYPPI_KOODI\": \"100340\"}, \"geometry_name\": \"GEOLOC\"}";
    public static final String testInvalidSingleJson2 = "{\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.643239226767022, 61.519112683582854]}, \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_6769212\", \"TYYPPI_KOODI\": \"105007\", \"KATUVALO_ID\": 405172, \"LAMPPU_TYYPPI\": \"ST 100 (SIEMENS)\", \"LAMPPU_TYYPPI_KOODI\": \"100340\"}, \"geometry_name\": \"GEOLOC\"}";

}
