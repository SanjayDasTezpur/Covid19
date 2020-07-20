package com.covid19.ne.corona.entities;

/* sanjayda created on 4/5/2020 inside the package - com.covid19.ne.corona.entities */

import com.covid19.ne.corona.uitility.DateUtility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GraphData implements Serializable {
    private transient Set<Date> xData;
    private List<String> xDataV2;
    private List<YData> yData;

    public void proessData() {
        List<String> lst = new ArrayList<>();
        xData.forEach(date -> lst.add(DateUtility.getDate(date)));
        setXDataV2(lst);
    }
}



