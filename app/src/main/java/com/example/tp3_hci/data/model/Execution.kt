package com.example.tp3_hci.data.model

import java.util.Date

data class Execution (
    var id: Int,
    var date: Date,
    var duration: Int,
    var wasModified: Boolean,
    var routineOverview: RoutineOverview
)