package com.covid19.ne.corona.entities;

/* sanjayda created on 4/3/2020 inside the package - com.covid19.ne.corona.entities */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private String lastUpdateTime;
    private Object data;
    private Object overview;
}


