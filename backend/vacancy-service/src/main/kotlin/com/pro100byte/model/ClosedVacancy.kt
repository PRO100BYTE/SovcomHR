package com.pro100byte.model

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "CLOSED_VACANCIES")
class ClosedVacancy : Vacancy()