package com.alpha.entities.reports;

import java.util.Objects;

public class Report {
    private int reportId;
    private String buyRequestId;
    private String sellRequestId;
    private int assetsCount;

    public Report(int reportId, String buyRequestId, String sellRequestId, int assetsCount) {
        this.reportId = reportId;
        this.buyRequestId = buyRequestId;
        this.sellRequestId = sellRequestId;
        this.assetsCount = assetsCount;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getBuyRequestId() {
        return buyRequestId;
    }

    public void setBuyRequestId(String buyRequestId) {
        this.buyRequestId = buyRequestId;
    }

    public String getSellRequestId() {
        return sellRequestId;
    }

    public void setSellRequestId(String sellRequestId) {
        this.sellRequestId = sellRequestId;
    }

    public int getAssetsCount() {
        return assetsCount;
    }

    public void setAssetsCount(int assetsCount) {
        this.assetsCount = assetsCount;
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", buyRequestId='" + buyRequestId + '\'' +
                ", sellRequestId='" + sellRequestId + '\'' +
                ", assetsCount=" + assetsCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return reportId == report.reportId &&
                assetsCount == report.assetsCount &&
                Objects.equals(buyRequestId, report.buyRequestId) &&
                Objects.equals(sellRequestId, report.sellRequestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId, buyRequestId, sellRequestId, assetsCount);
    }
}
