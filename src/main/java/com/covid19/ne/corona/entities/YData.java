package com.covid19.ne.corona.entities;

/* sanjayda created on 4/5/2020 inside the package - com.covid19.ne.corona.entities */


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class YData {
    private List<Integer> data;
    private String name;
}



