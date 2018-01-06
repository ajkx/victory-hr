package com.victory.hrm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.victory.attendance.entity.AttendanceGroup;
import com.victory.attendance.entity.Card;
import com.victory.common.entity.BaseEntity;

import javax.persistence.*;


/**
 * Created by ajkx
 * Date: 2017/7/31.
 * Time:9:42
 */
@Entity
@Table(name = "hrmresource")
public class HrmResource extends BaseEntity<Long> {

    //=======基本信息=======
    //姓名
    @Column(name = "lastname")
    private String name;

    //工号
    @Column(name = "workcode")
    private String workCode;

    //拼音首字母
    @Column(name = "pinyinlastname")
    private String shortName;

    //性别
    @Column(name = "sex")
    private String sex;

    //生日
    @Column(name = "birthday")
    private String birthday;

    //婚姻状态
    @Column(name = "maritalstatus")
    private String maritalStatus;

    //移动电话
    @Column(name = "mobile")
    private String mobile;

    //电子邮件
    @Column(name = "email")
    private String email;

    //身份证号码
    @Column(name = "certificatenum")
    private String certificateNum;

    //户口所在地
    @Column(name = "nativeplace")
    private String nativePlace;

    //家庭住址
    @Column(name = "homeaddress")
    private String homeAddress;

    //民族
    @Column(name = "folk")
    private String folk;

   // =======工作信息========
   // 分部id
    @ManyToOne(targetEntity = HrmSubCompany.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "subcompanyid1")
    private HrmSubCompany subCompany;

    //部门id
    @ManyToOne(targetEntity = HrmDepartment.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "departmentid")
    private HrmDepartment department;
//
//    //岗位id
//    @ManyToOne(targetEntity = HrmJobTitle.class,fetch = FetchType.LAZY)
//    @JoinColumn(name = "jobtitle")
//    private HrmJobTitle jobTitle;

    //递归上级字符串 ,隔开
    @Column(name = "managerstr")
    private String manager;

    @Column(name = "status")
    private HrmStatus status;

    //试用结束(转正)日期  开始日期为createDate
    @Column(name = "probationenddate")
    private String probationEnddate;

    //创建日期
    @Column(name = "createdate")
    private String createDate;

    // ========考勤信息==========
    @JsonBackReference
    @ManyToOne(targetEntity = AttendanceGroup.class,fetch = FetchType.LAZY)
    @JoinTable(name = "EHR_group_resource",
            joinColumns =@JoinColumn(name = "resource_id",referencedColumnName = "id",unique = true),
            inverseJoinColumns = @JoinColumn(name = "group_id",referencedColumnName = "id"))
    private AttendanceGroup group;

    // 绑定的IC卡
    @OneToOne(targetEntity = Card.class,mappedBy = "resource",fetch = FetchType.LAZY)
    private Card card;

    public AttendanceGroup getGroup() {
        return group;
    }

    public void setGroup(AttendanceGroup group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCertificateNum() {
        return certificateNum;
    }

    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getFolk() {
        return folk;
    }

    public void setFolk(String folk) {
        this.folk = folk;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public HrmStatus getStatus() {
        return status;
    }

    public void setStatus(HrmStatus status) {
        this.status = status;
    }

    public String getProbationEnddate() {
        return probationEnddate;
    }

    public void setProbationEnddate(String probationEnddate) {
        this.probationEnddate = probationEnddate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public HrmSubCompany getSubCompany() {
        return subCompany;
    }

    public void setSubCompany(HrmSubCompany subCompany) {
        this.subCompany = subCompany;
    }

    public HrmDepartment getDepartment() {
        return department;
    }

    public void setDepartment(HrmDepartment department) {
        this.department = department;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        HrmResource that = (HrmResource) o;

        return getId() == that.getId();

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }
}
