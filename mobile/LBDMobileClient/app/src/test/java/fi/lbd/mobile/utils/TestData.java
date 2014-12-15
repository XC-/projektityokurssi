package fi.lbd.mobile.utils;

/**
 * Helper data for REST api requests.
 *
 * Created by Tommi.
 */
public class TestData {
    //    URLResponse response1 = URLReader.get("http://lbdbackend.ignorelist.com/locationdata/api/Streetlights/inarea/?xbottomleft=23.645&ybottomleft=61.515&ytopright=61.52&xtopright=23.65");
    //    Log.e("Data", response1.getContents());

    public static final String testJson = "{\"totalFeatures\": 2, \"type\": \"FeatureCollection\", \"features\": [{\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.64941278370676, 61.5192743640121]}, \"id\": \"WFS_KATUVALO.391441\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_4703229\", \"TYYPPI_KOODI\": \"105030\", \"KATUVALO_ID\": 391441, \"LAMPPU_TYYPPI\": \"8512 (IDMAN)\", \"LAMPPU_TYYPPI_KOODI\": \"100105\"}, \"geometry_name\": \"GEOLOC\"}, {\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.64941278370676, 61.5192743640121]}, \"id\": \"WFS_KATUVALO.391440\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_4703228\", \"TYYPPI_KOODI\": \"105030\", \"KATUVALO_ID\": 391440, \"LAMPPU_TYYPPI\": \"8512 (IDMAN)\", \"LAMPPU_TYYPPI_KOODI\": \"100105\"}, \"geometry_name\": \"GEOLOC\"}]}";
    public static final String testInvalidJson1 = "{\"totalFeatures\": 2, \"type\": \"FeatureCollection\", \"features\": [{\"geometry\": {\"type\": \"Point\", \"id\": \"WFS_KATUVALO.391441\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_4703229\", \"TYYPPI_KOODI\": \"105030\", \"KATUVALO_ID\": 391441, \"LAMPPU_TYYPPI\": \"8512 (IDMAN)\", \"LAMPPU_TYYPPI_KOODI\": \"100105\"}, \"geometry_name\": \"GEOLOC\"}, {\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.64941278370676, 61.5192743640121]}, \"id\": \"WFS_KATUVALO.391440\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_4703228\", \"TYYPPI_KOODI\": \"105030\", \"KATUVALO_ID\": 391440, \"LAMPPU_TYYPPI\": \"8512 (IDMAN)\", \"LAMPPU_TYYPPI_KOODI\": \"100105\"}, \"geometry_name\": \"GEOLOC\"}]}";
    public static final String testInvalidJson2 = "{\"totalFeatures\": 2, \"type\": \"FeatureCollection\", \"features\": [{\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.64941278370676, 61.5192743640121]}, \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_4703229\", \"TYYPPI_KOODI\": \"105030\", \"KATUVALO_ID\": 391441, \"LAMPPU_TYYPPI\": \"8512 (IDMAN)\", \"LAMPPU_TYYPPI_KOODI\": \"100105\"}, \"geometry_name\": \"GEOLOC\"}, {\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.64941278370676, 61.5192743640121]}, \"id\": \"WFS_KATUVALO.391440\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_4703228\", \"TYYPPI_KOODI\": \"105030\", \"KATUVALO_ID\": 391440, \"LAMPPU_TYYPPI\": \"8512 (IDMAN)\", \"LAMPPU_TYYPPI_KOODI\": \"100105\"}, \"geometry_name\": \"GEOLOC\"}]}";

    public static final String testJsonMini = "{\"totalFeatures\": 2, \"type\": \"FeatureCollection\", \"features\": [{\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.64941278370676, 61.5192743640121]}, \"type\": \"Feature\", \"id\": \"WFS_KATUVALO.391441\"}, {\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.64941278370676, 61.5192743640121]}, \"type\": \"Feature\", \"id\": \"WFS_KATUVALO.391440\"}]}";
    public static final String testSingleJson = "{\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.643239226767022, 61.519112683582854]}, \"id\": \"WFS_KATUVALO.405172\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_6769212\", \"TYYPPI_KOODI\": \"105007\", \"KATUVALO_ID\": 405172, \"LAMPPU_TYYPPI\": \"ST 100 (SIEMENS)\", \"LAMPPU_TYYPPI_KOODI\": \"100340\"}, \"geometry_name\": \"GEOLOC\"}";
    public static final String testInvalidSingleJson1 = "{\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.643239226767022, 61.519112683582854]}, \"id\": \"WFS_KATUVALO.405172\", \"type\": \"Feature\"s\": {\"NIMI\": \"XPWR_6769212\", \"TYYPPI_KOODI\": \"105007\", \"KATUVALO_ID\": 405172, \"LAMPPU_TYYPPI\": \"ST 100 (SIEMENS)\", \"LAMPPU_TYYPPI_KOODI\": \"100340\"}, \"geometry_name\": \"GEOLOC\"}";
    public static final String testInvalidSingleJson2 = "{\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.643239226767022, 61.519112683582854]}, \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_6769212\", \"TYYPPI_KOODI\": \"105007\", \"KATUVALO_ID\": 405172, \"LAMPPU_TYYPPI\": \"ST 100 (SIEMENS)\", \"LAMPPU_TYYPPI_KOODI\": \"100340\"}, \"geometry_name\": \"GEOLOC\"}";

    public static final String testSearchResult = "{\"totalResults\": 1137, \"from\": \"id\", \"search\": \"*365*\", \"limit\": 10, \"results\": {\"totalFeatures\": 10, \"type\": \"FeatureCollection\", \"features\": [{\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.853010926432248, 61.44259598316407]}, \"id\": \"WFS_KATUVALO.362365\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_3594456\", \"TYYPPI_KOODI\": \"105071\", \"KATUVALO_ID\": 362365, \"LAMPPU_TYYPPI\": \"Ei m\\u00e4\\u00e4ritelty (TUNTEMATON)\", \"LAMPPU_TYYPPI_KOODI\": \"100991\"}, \"geometry_name\": \"GEOLOC\"}, {\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.844019700831016, 61.44237983567443]}, \"id\": \"WFS_KATUVALO.363365\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_3594127\", \"TYYPPI_KOODI\": \"105004\", \"KATUVALO_ID\": 363365, \"LAMPPU_TYYPPI\": \"Ei m\\u00e4\\u00e4ritelty (TUNTEMATON)\", \"LAMPPU_TYYPPI_KOODI\": \"100990\"}, \"geometry_name\": \"GEOLOC\"}, {\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.849767162346282, 61.439269495678744]}, \"id\": \"WFS_KATUVALO.363650\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_3510823\", \"TYYPPI_KOODI\": \"105005\", \"KATUVALO_ID\": 363650, \"LAMPPU_TYYPPI\": \"Ei m\\u00e4\\u00e4ritelty (TUNTEMATON)\", \"LAMPPU_TYYPPI_KOODI\": \"100990\"}, \"geometry_name\": \"GEOLOC\"}, {\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.850248875031784, 61.43922217773178]}, \"id\": \"WFS_KATUVALO.363651\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_3510824\", \"TYYPPI_KOODI\": \"105005\", \"KATUVALO_ID\": 363651, \"LAMPPU_TYYPPI\": \"Ei m\\u00e4\\u00e4ritelty (TUNTEMATON)\", \"LAMPPU_TYYPPI_KOODI\": \"100990\"}, \"geometry_name\": \"GEOLOC\"}, {\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.849137024358146, 61.439338591258554]}, \"id\": \"WFS_KATUVALO.363652\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_3510825\", \"TYYPPI_KOODI\": \"105005\", \"KATUVALO_ID\": 363652, \"LAMPPU_TYYPPI\": \"Ei m\\u00e4\\u00e4ritelty (TUNTEMATON)\", \"LAMPPU_TYYPPI_KOODI\": \"100990\"}, \"geometry_name\": \"GEOLOC\"}, {\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.848493013957185, 61.43939946593371]}, \"id\": \"WFS_KATUVALO.363653\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_3510826\", \"TYYPPI_KOODI\": \"105005\", \"KATUVALO_ID\": 363653, \"LAMPPU_TYYPPI\": \"Ei m\\u00e4\\u00e4ritelty (TUNTEMATON)\", \"LAMPPU_TYYPPI_KOODI\": \"100990\"}, \"geometry_name\": \"GEOLOC\"}, {\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.850988546248857, 61.43914888525531]}, \"id\": \"WFS_KATUVALO.363654\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_3510827\", \"TYYPPI_KOODI\": \"105005\", \"KATUVALO_ID\": 363654, \"LAMPPU_TYYPPI\": \"Ei m\\u00e4\\u00e4ritelty (TUNTEMATON)\", \"LAMPPU_TYYPPI_KOODI\": \"100990\"}, \"geometry_name\": \"GEOLOC\"}, {\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.84917623040533, 61.4390725771073]}, \"id\": \"WFS_KATUVALO.363655\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_3510828\", \"TYYPPI_KOODI\": \"105005\", \"KATUVALO_ID\": 363655, \"LAMPPU_TYYPPI\": \"Ei m\\u00e4\\u00e4ritelty (TUNTEMATON)\", \"LAMPPU_TYYPPI_KOODI\": \"100991\"}, \"geometry_name\": \"GEOLOC\"}, {\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.849452656925127, 61.43927072591714]}, \"id\": \"WFS_KATUVALO.363656\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_3510829\", \"TYYPPI_KOODI\": \"105005\", \"KATUVALO_ID\": 363656, \"LAMPPU_TYYPPI\": \"Ei m\\u00e4\\u00e4ritelty (TUNTEMATON)\", \"LAMPPU_TYYPPI_KOODI\": \"100991\"}, \"geometry_name\": \"GEOLOC\"}, {\"geometry\": {\"type\": \"Point\", \"coordinates\": [23.848857425375986, 61.43884321798061]}, \"id\": \"WFS_KATUVALO.363657\", \"type\": \"Feature\", \"properties\": {\"NIMI\": \"XPWR_3510830\", \"TYYPPI_KOODI\": \"105005\", \"KATUVALO_ID\": 363657, \"LAMPPU_TYYPPI\": \"Ei m\\u00e4\\u00e4ritelty (TUNTEMATON)\", \"LAMPPU_TYYPPI_KOODI\": \"100991\"}, \"geometry_name\": \"GEOLOC\"}]}}";
}
