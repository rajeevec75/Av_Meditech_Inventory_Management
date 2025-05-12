/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.entities;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sale_service_master")
public class PerformService implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_service_id")
    private Integer saleServiceId;

    @Column(name = "service_date")
    @Temporal(TemporalType.DATE)
    private Date serviceDate;

    @Column(name = "remarks", length = 1000)
    private String problemRemarks;

    @Column(name = "solution", length = 1000)
    private String solution;

    @Column(name = "attachment")
    private String attachment;

    @Column(name = "sale_id", nullable = false)
    private Integer saleId;

    public Integer getSaleServiceId() {
        return saleServiceId;
    }

    public void setSaleServiceId(Integer saleServiceId) {
        this.saleServiceId = saleServiceId;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getProblemRemarks() {
        return problemRemarks;
    }

    public void setProblemRemarks(String problemRemarks) {
        this.problemRemarks = problemRemarks;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

}
