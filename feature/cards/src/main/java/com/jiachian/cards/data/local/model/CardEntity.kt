package com.jiachian.cards.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_card")
data class CardEntity(
    @PrimaryKey @ColumnInfo("id") val id: Int,
    @ColumnInfo("card_name") val cardName: String,
    @ColumnInfo("name_on_card") val nameOnCard: String,
    @ColumnInfo("card_number") val cardNumberEncrypted: String,
    @ColumnInfo("exp_date") val expDate: Int,
    @ColumnInfo("cvv") val cvvEncrypted: String,
)