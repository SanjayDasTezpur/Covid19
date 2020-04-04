package com.covid19.ne.corona.entities.qualifiers;

import java.io.Serializable;

/* sanjayda created on 4/4/2020 inside the package - com.covid19.ne.corona.entities.qualifiers */


public interface IPersistency extends Serializable {
    void makePersistable() throws Exception;
}


