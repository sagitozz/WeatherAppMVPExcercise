package com.example.weatherappmvpexcercise.network.dto

class Datum {

    @SerializedName("rh")
    @Expose
    private Integer rh;
    @SerializedName("pod")
    @Expose
    private String pod;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("pres")
    @Expose
    private Double pres;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("ob_time")
    @Expose
    private String obTime;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("clouds")
    @Expose
    private Integer clouds;
    @SerializedName("ts")
    @Expose
    private Integer ts;
    @SerializedName("solar_rad")
    @Expose
    private Integer solarRad;
    @SerializedName("state_code")
    @Expose
    private String stateCode;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("wind_spd")
    @Expose
    private Double windSpd;
    @SerializedName("last_ob_time")
    @Expose
    private String lastObTime;
    @SerializedName("wind_cdir_full")
    @Expose
    private String windCdirFull;
    @SerializedName("wind_cdir")
    @Expose
    private String windCdir;
    @SerializedName("slp")
    @Expose
    private Double slp;
    @SerializedName("vis")
    @Expose
    private Double vis;
    @SerializedName("h_angle")
    @Expose
    private Integer hAngle;
    @SerializedName("sunset")
    @Expose
    private String sunset;
    @SerializedName("dni")
    @Expose
    private Integer dni;
    @SerializedName("dewpt")
    @Expose
    private Double dewpt;
    @SerializedName("snow")
    @Expose
    private Integer snow;
    @SerializedName("uv")
    @Expose
    private Integer uv;
    @SerializedName("precip")
    @Expose
    private Integer precip;
    @SerializedName("wind_dir")
    @Expose
    private Integer windDir;
    @SerializedName("sunrise")
    @Expose
    private String sunrise;
    @SerializedName("ghi")
    @Expose
    private Integer ghi;
    @SerializedName("dhi")
    @Expose
    private Integer dhi;
    @SerializedName("aqi")
    @Expose
    private Integer aqi;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("weather")
    @Expose
    private Weather weather;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("temp")
    @Expose
    private Double temp;
    @SerializedName("station")
    @Expose
    private String station;
    @SerializedName("elev_angle")
    @Expose
    private Double elevAngle;
    @SerializedName("app_temp")
    @Expose
    private Double appTemp;

    public Integer getRh() {
        return rh;
    }

    public void setRh(Integer rh) {
        this.rh = rh;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getPres() {
        return pres;
    }

    public void setPres(Double pres) {
        this.pres = pres;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getObTime() {
        return obTime;
    }

    public void setObTime(String obTime) {
        this.obTime = obTime;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getClouds() {
        return clouds;
    }

    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    public Integer getTs() {
        return ts;
    }

    public void setTs(Integer ts) {
        this.ts = ts;
    }

    public Integer getSolarRad() {
        return solarRad;
    }

    public void setSolarRad(Integer solarRad) {
        this.solarRad = solarRad;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getWindSpd() {
        return windSpd;
    }

    public void setWindSpd(Double windSpd) {
        this.windSpd = windSpd;
    }

    public String getLastObTime() {
        return lastObTime;
    }

    public void setLastObTime(String lastObTime) {
        this.lastObTime = lastObTime;
    }

    public String getWindCdirFull() {
        return windCdirFull;
    }

    public void setWindCdirFull(String windCdirFull) {
        this.windCdirFull = windCdirFull;
    }

    public String getWindCdir() {
        return windCdir;
    }

    public void setWindCdir(String windCdir) {
        this.windCdir = windCdir;
    }

    public Double getSlp() {
        return slp;
    }

    public void setSlp(Double slp) {
        this.slp = slp;
    }

    public Double getVis() {
        return vis;
    }

    public void setVis(Double vis) {
        this.vis = vis;
    }

    public Integer getHAngle() {
        return hAngle;
    }

    public void setHAngle(Integer hAngle) {
        this.hAngle = hAngle;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public Double getDewpt() {
        return dewpt;
    }

    public void setDewpt(Double dewpt) {
        this.dewpt = dewpt;
    }

    public Integer getSnow() {
        return snow;
    }

    public void setSnow(Integer snow) {
        this.snow = snow;
    }

    public Integer getUv() {
        return uv;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public Integer getPrecip() {
        return precip;
    }

    public void setPrecip(Integer precip) {
        this.precip = precip;
    }

    public Integer getWindDir() {
        return windDir;
    }

    public void setWindDir(Integer windDir) {
        this.windDir = windDir;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getGhi() {
        return ghi;
    }

    public void setGhi(Integer ghi) {
        this.ghi = ghi;
    }

    public Integer getDhi() {
        return dhi;
    }

    public void setDhi(Integer dhi) {
        this.dhi = dhi;
    }

    public Integer getAqi() {
        return aqi;
    }

    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Double getElevAngle() {
        return elevAngle;
    }

    public void setElevAngle(Double elevAngle) {
        this.elevAngle = elevAngle;
    }

    public Double getAppTemp() {
        return appTemp;
    }

    public void setAppTemp(Double appTemp) {
        this.appTemp = appTemp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("rh", rh).append("pod", pod).append("lon", lon).append("pres", pres).append("timezone", timezone).append("obTime", obTime).append("countryCode", countryCode).append("clouds", clouds).append("ts", ts).append("solarRad", solarRad).append("stateCode", stateCode).append("cityName", cityName).append("windSpd", windSpd).append("lastObTime", lastObTime).append("windCdirFull", windCdirFull).append("windCdir", windCdir).append("slp", slp).append("vis", vis).append("hAngle", hAngle).append("sunset", sunset).append("dni", dni).append("dewpt", dewpt).append("snow", snow).append("uv", uv).append("precip", precip).append("windDir", windDir).append("sunrise", sunrise).append("ghi", ghi).append("dhi", dhi).append("aqi", aqi).append("lat", lat).append("weather", weather).append("datetime", datetime).append("temp", temp).append("station", station).append("elevAngle", elevAngle).append("appTemp", appTemp).toString();
    }

}
