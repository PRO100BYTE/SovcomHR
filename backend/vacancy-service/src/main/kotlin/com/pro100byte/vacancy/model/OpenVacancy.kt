package com.pro100byte.vacancy.model

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "OPEN_VACANCIES")
class OpenVacancy : Vacancy()
