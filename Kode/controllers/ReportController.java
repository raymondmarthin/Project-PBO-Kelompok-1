package controllers;

import dao.ReportDAO;

public class ReportController {
    public static String getDailyReport(String date) {
        return ReportDAO.getDailyReport(date);
    }

    public static String getMonthlyReport(String month) {
        return ReportDAO.getMonthlyReport(month);
    }
}
