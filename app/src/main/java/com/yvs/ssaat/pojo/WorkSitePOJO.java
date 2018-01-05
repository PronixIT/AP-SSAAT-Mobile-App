package com.yvs.ssaat.pojo;

/**
 * Created by NAVEEN KS on 12/30/2017.
 */

public class WorkSitePOJO {
    private String sno, district_code, mandal_code, panchayat_code, village_code, habitation_code, ssaat_code, work_code, work_name, work_name_telugu, work_location,
            work_location_telugu, task_code, task_name, skill_type, qty_sanc, amount_sanc, qty_done, amount_spent, audit_is_work_done,
            audit_is_work_done_location, audit_qty_sanc, audit_amount_sanc, audit_qty_done, audit_amount_spent, audit_remarks, status,
            sent_file_name, sent_date, resp_filename, resp_date, created_date, department, audit_usefull_work;

    private String is_workdone;
    private String checkvalues_measurment;
    private String checkvalues_total;
    private String difference_measurments;
    private String difference_total;
    private String resp_personnam;
    private String resp_person_jobdesg;
    private String impofwork;


    private String comments;

    public WorkSitePOJO( String sno, String district_code, String mandal_code, String panchayat_code, String village_code, String habitation_code, String ssaat_code,
                         String work_code, String work_name, String work_name_telugu, String work_location, String work_location_telugu, String task_code,
                         String task_name, String skill_type, String qty_sanc, String amount_sanc, String qty_done, String amount_spent,
                         String audit_is_work_done, String audit_is_work_done_location, String audit_qty_sanc, String audit_amount_sanc, String audit_qty_done,
                         String audit_amount_spent, String audit_remarks, String status, String sent_file_name, String sent_date, String resp_filename,
                         String resp_date, String created_date, String department, String audit_usefull_work, String is_workdone, String checkvalues_measurment,
                         String checkvalues_total, String difference_measurments, String difference_total, String resp_personnam,
                         String resp_person_jobdesg,String impofwork, String comments){
        this.sno = sno;
        this.district_code = district_code;
        this.mandal_code = mandal_code;
        this.panchayat_code = panchayat_code;
        this.village_code = village_code;
        this.habitation_code = habitation_code;
        this.ssaat_code = ssaat_code;
        this.work_code = work_code;
        this.work_name = work_name;
        this.work_name_telugu = work_name_telugu;
        this.work_location = work_location;
        this.work_location_telugu = work_location_telugu;
        this.task_code = task_code;
        this.task_name = task_name;
        this.skill_type = skill_type;
        this.qty_sanc = qty_sanc;
        this.amount_sanc = amount_sanc;
        this.qty_done = qty_done;
        this.amount_spent = amount_spent;
        this.audit_is_work_done = audit_is_work_done;
        this.audit_is_work_done_location = audit_is_work_done_location;
        this.audit_qty_sanc = audit_qty_sanc;
        this.audit_amount_sanc = audit_amount_sanc;
        this.audit_qty_done = audit_qty_done;
        this.audit_amount_spent = audit_amount_spent;
        this.audit_remarks = audit_remarks;
        this.status = status;
        this.sent_file_name = sent_file_name;
        this.sent_date = sent_date;
        this.resp_filename = resp_filename;
        this.resp_date = resp_date;
        this.created_date = created_date;
        this.department = department;
        this.audit_usefull_work = audit_usefull_work;
        this.is_workdone = is_workdone;
        this.checkvalues_measurment = checkvalues_measurment;
        this.checkvalues_total = checkvalues_total;
        this.difference_measurments = difference_measurments;
        this.difference_total = difference_total;
        this.resp_personnam = resp_personnam;
        this.resp_person_jobdesg = resp_person_jobdesg;
        this.impofwork = impofwork;
        this.comments = comments;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(String district_code) {
        this.district_code = district_code;
    }

    public String getMandal_code() {
        return mandal_code;
    }

    public void setMandal_code(String mandal_code) {
        this.mandal_code = mandal_code;
    }

    public String getPanchayat_code() {
        return panchayat_code;
    }

    public void setPanchayat_code(String panchayat_code) {
        this.panchayat_code = panchayat_code;
    }

    public String getVillage_code() {
        return village_code;
    }

    public void setVillage_code(String village_code) {
        this.village_code = village_code;
    }

    public String getHabitation_code() {
        return habitation_code;
    }

    public void setHabitation_code(String habitation_code) {
        this.habitation_code = habitation_code;
    }

    public String getSsaat_code() {
        return ssaat_code;
    }

    public void setSsaat_code(String ssaat_code) {
        this.ssaat_code = ssaat_code;
    }

    public String getWork_code() {
        return work_code;
    }

    public void setWork_code(String work_code) {
        this.work_code = work_code;
    }

    public String getWork_name() {
        return work_name;
    }

    public void setWork_name(String work_name) {
        this.work_name = work_name;
    }

    public String getWork_name_telugu() {
        return work_name_telugu;
    }

    public void setWork_name_telugu(String work_name_telugu) {
        this.work_name_telugu = work_name_telugu;
    }

    public String getWork_location() {
        return work_location;
    }

    public void setWork_location(String work_location) {
        this.work_location = work_location;
    }

    public String getWork_location_telugu() {
        return work_location_telugu;
    }

    public void setWork_location_telugu(String work_location_telugu) {
        this.work_location_telugu = work_location_telugu;
    }

    public String getTask_code() {
        return task_code;
    }

    public void setTask_code(String task_code) {
        this.task_code = task_code;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getSkill_type() {
        return skill_type;
    }

    public void setSkill_type(String skill_type) {
        this.skill_type = skill_type;
    }

    public String getQty_sanc() {
        return qty_sanc;
    }

    public void setQty_sanc(String qty_sanc) {
        this.qty_sanc = qty_sanc;
    }

    public String getAmount_sanc() {
        return amount_sanc;
    }

    public void setAmount_sanc(String amount_sanc) {
        this.amount_sanc = amount_sanc;
    }

    public String getQty_done() {
        return qty_done;
    }

    public void setQty_done(String qty_done) {
        this.qty_done = qty_done;
    }

    public String getAmount_spent() {
        return amount_spent;
    }

    public void setAmount_spent(String amount_spent) {
        this.amount_spent = amount_spent;
    }

    public String getAudit_is_work_done() {
        return audit_is_work_done;
    }

    public void setAudit_is_work_done(String audit_is_work_done) {
        this.audit_is_work_done = audit_is_work_done;
    }

    public String getAudit_is_work_done_location() {
        return audit_is_work_done_location;
    }

    public void setAudit_is_work_done_location(String audit_is_work_done_location) {
        this.audit_is_work_done_location = audit_is_work_done_location;
    }

    public String getAudit_qty_sanc() {
        return audit_qty_sanc;
    }

    public void setAudit_qty_sanc(String audit_qty_sanc) {
        this.audit_qty_sanc = audit_qty_sanc;
    }

    public String getAudit_amount_sanc() {
        return audit_amount_sanc;
    }

    public void setAudit_amount_sanc(String audit_amount_sanc) {
        this.audit_amount_sanc = audit_amount_sanc;
    }

    public String getAudit_qty_done() {
        return audit_qty_done;
    }

    public void setAudit_qty_done(String audit_qty_done) {
        this.audit_qty_done = audit_qty_done;
    }

    public String getAudit_amount_spent() {
        return audit_amount_spent;
    }

    public void setAudit_amount_spent(String audit_amount_spent) {
        this.audit_amount_spent = audit_amount_spent;
    }

    public String getAudit_remarks() {
        return audit_remarks;
    }

    public void setAudit_remarks(String audit_remarks) {
        this.audit_remarks = audit_remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSent_file_name() {
        return sent_file_name;
    }

    public void setSent_file_name(String sent_file_name) {
        this.sent_file_name = sent_file_name;
    }

    public String getSent_date() {
        return sent_date;
    }

    public void setSent_date(String sent_date) {
        this.sent_date = sent_date;
    }

    public String getResp_filename() {
        return resp_filename;
    }

    public void setResp_filename(String resp_filename) {
        this.resp_filename = resp_filename;
    }

    public String getResp_date() {
        return resp_date;
    }

    public void setResp_date(String resp_date) {
        this.resp_date = resp_date;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAudit_usefull_work() {
        return audit_usefull_work;
    }

    public void setAudit_usefull_work(String audit_usefull_work) {
        this.audit_usefull_work = audit_usefull_work;
    }
    public String getIs_workdone() {
        return is_workdone;
    }

    public void setIs_workdone(String is_workdone) {
        this.is_workdone = is_workdone;
    }

    public String getCheckvalues_measurment() {
        return checkvalues_measurment;
    }

    public void setCheckvalues_measurment(String checkvalues_measurment) {
        this.checkvalues_measurment = checkvalues_measurment;
    }

    public String getCheckvalues_total() {
        return checkvalues_total;
    }

    public void setCheckvalues_total(String checkvalues_total) {
        this.checkvalues_total = checkvalues_total;
    }

    public String getDifference_measurments() {
        return difference_measurments;
    }

    public void setDifference_measurments(String difference_measurments) {
        this.difference_measurments = difference_measurments;
    }

    public String getDifference_total() {
        return difference_total;
    }

    public void setDifference_total(String difference_total) {
        this.difference_total = difference_total;
    }

    public String getResp_personnam() {
        return resp_personnam;
    }

    public void setResp_personnam(String resp_personnam) {
        this.resp_personnam = resp_personnam;
    }

    public String getResp_person_jobdesg() {
        return resp_person_jobdesg;
    }

    public void setResp_person_jobdesg(String resp_person_jobdesg) {
        this.resp_person_jobdesg = resp_person_jobdesg;
    }

    public String getImpofwork() {
        return impofwork;
    }

    public void setImpofwork(String impofwork) {
        this.impofwork = impofwork;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
