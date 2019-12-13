CREATE TABLE gibdd_auto(
    id_auto BIGSERIAL,
    autonumber VARCHAR(10),
    engine VARCHAR(20),
    color VARCHAR(20),
    model VARCHAR(20),
    passport VARCHAR(30),
    PRIMARY KEY (id_auto)
);

CREATE TABLE gibdd_employee(
    id_employee BIGSERIAL,
    FIO VARCHAR(40),
    rank VARCHAR(20),
    position VARCHAR(20),
    PRIMARY KEY (id_employee)
);

CREATE TABLE gibdd_owner(
    id_owner BIGSERIAL,
    certificate VARCHAR(30),
    FIO VARCHAR(40),
    adress VARCHAR(40),
    gender VARCHAR(10),
    year INT,
    PRIMARY KEY (id_owner)
);


CREATE TABLE gibdd_look(
    id_look BIGSERIAL,
    id_auto INT,
    id_owner INT,
    date_look DATE,
    conlusion VARCHAR(5),
    id_employee INT,
    PRIMARY KEY (id_look),
    FOREIGN KEY (id_auto) REFERENCES gibdd_auto(id_auto)  ON delete  set null,
    FOREIGN KEY (id_owner) REFERENCES gibdd_owner(id_owner)  ON delete  set null,
    FOREIGN KEY (id_employee) REFERENCES gibdd_employee(id_employee)  ON delete  set null
);