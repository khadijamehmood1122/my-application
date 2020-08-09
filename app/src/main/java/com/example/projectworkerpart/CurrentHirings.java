package com.example.projectworkerpart;

import java.io.Serializable;
import java.util.ArrayList;

public class CurrentHirings
{


    String Worker_EmailId;
    String Customer_EmailId;
    Boolean Status;
    String Statement_of_work;

    public CurrentHirings(String worker_EmailId, String customer_EmailId, Boolean status, String statement_of_work) {
        Worker_EmailId = worker_EmailId;
        Customer_EmailId = customer_EmailId;
        Status = status;
        Statement_of_work = statement_of_work;
    }


    public String getWorker_EmailId() {
        return Worker_EmailId;
    }

    public String getCustomer_EmailId() {
        return Customer_EmailId;
    }

    public Boolean getStatus() {
        return Status;
    }
    public void setStatus(Boolean status) {
        Status = status;
    }


    public String getStatement_of_work() {
        return Statement_of_work;
    }

    public void setWorker_EmailId(String worker_EmailId) {
        Worker_EmailId = worker_EmailId;
    }

    public void setCustomer_EmailId(String customer_EmailId) {
        Customer_EmailId = customer_EmailId;
    }

    public void setStatement_of_work(String statement_of_work) {
        Statement_of_work = statement_of_work;
    }

}
