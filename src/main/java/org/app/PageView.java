package org.app;

import java.sql.Date;
import java.util.Objects;

public class PageView {
    private Date timeCreate;
    private Date cookieCreate;
    private int browserCode;
    private String browserVer;
    private int osCode;
    private String osVer;
    private long ip;
    private int locId;
    private String domain;
    private int siteId;
    private int cId;
    private String path;
    private String referer;
    private long guid;
    private String flashVersion;
    private String jre;
    private String sr;
    private String sc;
    private int geographic;
    private String category;
    public PageView(Date timeCreate, Date cookieCreate, int browserCode, String browserVer, int osCode, String osVer,
                    long ip, int locId, String domain, int siteId, int cId, String path, String referer,
                    long guid, String flashVersion, String jre, String sr, String sc,
                    int geographic, String category) {
        this.timeCreate = timeCreate;
        this.cookieCreate = cookieCreate;
        this.browserCode = browserCode;
        this.browserVer = browserVer;
        this.osCode = osCode;
        this.osVer = osVer;
        this.ip = ip;
        this.locId = locId;
        this.domain = domain;
        this.siteId = siteId;
        this.cId = cId;
        this.path = path;
        this.referer = referer;
        this.guid = guid;
        this.flashVersion = flashVersion;
        this.jre = jre;
        this.sr = sr;
        this.sc = sc;
        this.geographic = geographic;
        this.category = category;
    }
//    timeCreate = 0
//    cookieCreate = 1
//    browserCode = 2
//    browserVer = 3
//    osCode = 4
//    osVer = 5
//    ip = 6 (kieu long)
//    locId = 7
//    domain = 8
//    siteId = 9
//    cId = 10
//    path = 11
//    referer = 12
//    guid = 13
//    flashVersion = 14
//    jre = 15
//    sr = 16
//    sc = 17
//    geographic = 18
//    category = 23
    public Date getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(Date timeCreate) {
        this.timeCreate = timeCreate;
    }

    public Date getCookieCreate() {
        return cookieCreate;
    }

    public void setCookieCreate(Date cookieCreate) {
        this.cookieCreate = cookieCreate;
    }

    public int getBrowserCode() {
        return browserCode;
    }

    public void setBrowserCode(int browserCode) {
        this.browserCode = browserCode;
    }

    public String getBrowserVer() {
        return browserVer;
    }

    public void setBrowserVer(String browserVer) {
        this.browserVer = browserVer;
    }

    public int getOsCode() {
        return osCode;
    }

    public void setOsCode(int osCode) {
        this.osCode = osCode;
    }

    public String getOsVer() {
        return osVer;
    }

    public void setOsVer(String osVer) {
        this.osVer = osVer;
    }

    public long getIp() {
        return ip;
    }

    public void setIp(long ip) {
        this.ip = ip;
    }

    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public long getGuid() {
        return guid;
    }

    public void setGuid(long guid) {
        this.guid = guid;
    }

    public String getFlashVersion() {
        return flashVersion;
    }

    public void setFlashVersion(String flashVersion) {
        this.flashVersion = flashVersion;
    }

    public String getJre() {
        return jre;
    }

    public void setJre(String jre) {
        this.jre = jre;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public int getGeographic() {
        return geographic;
    }

    public void setGeographic(int geographic) {
        this.geographic = geographic;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageView pageView = (PageView) o;
        return browserCode == pageView.browserCode && osCode == pageView.osCode && ip == pageView.ip && locId == pageView.locId && siteId == pageView.siteId && cId == pageView.cId && guid == pageView.guid && geographic == pageView.geographic && Objects.equals(timeCreate, pageView.timeCreate) && Objects.equals(cookieCreate, pageView.cookieCreate) && Objects.equals(browserVer, pageView.browserVer) && Objects.equals(osVer, pageView.osVer) && Objects.equals(domain, pageView.domain) && Objects.equals(path, pageView.path) && Objects.equals(referer, pageView.referer) && Objects.equals(flashVersion, pageView.flashVersion) && Objects.equals(jre, pageView.jre) && Objects.equals(sr, pageView.sr) && Objects.equals(sc, pageView.sc) && Objects.equals(category, pageView.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeCreate, cookieCreate, browserCode, browserVer, osCode, osVer, ip, locId, domain, siteId, cId, path, referer, guid, flashVersion, jre, sr, sc, geographic, category);
    }

    public static String catalog() {
        return "{\n" +
                "  \"table\":{\"namespace\":\"default\", \"name\":\"pageviewlog\"},\n" +
                "  \"rowkey\": \"key string :key\",\n" +
                "  \"columns\": {\n" +
                "    \"timeCreate string producer:timeCreate\",\n" +
                "    \"cookieCreate string producer:cookieCreate\",\n" +
                "    \"browserCode int hardware:browserCode\",\n" +
                "    \"browserVer string hardware:browserVer\",\n" +
                "    \"osCode int hardware:osCode\",\n" +
                "    \"osVer string hardware:osVer\",\n" +
                "    \"ip long consumer:ip\",\n" +
                "    \"locId int consumer:locId\",\n" +
                "    \"domain string producer:domain\",\n" +
                "    \"siteId int producer:siteId\",\n" +
                "    \"cId int consumer:cId\",\n" +
                "    \"path string producer:path\",\n" +
                "    \"referer string producer:referer\",\n" +
                "    \"guid long consumer:guid\",\n" +
                "    \"flashVersion string hardware:flashVersion\",\n" +
                "    \"jre string hardware:jre\",\n" +
                "    \"sr string hardware:sr\",\n" +
                "    \"sc string hardware:sc\",\n" +
                "    \"geographic int consumer:geographic\",\n" +
                "    \"category string producer:category\"\n" +
                "  }\n" +
                "}";

    }
}
