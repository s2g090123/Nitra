package com.jiachian.cards.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_card",
    indices = [Index(value = ["card_number"], unique = true)],
)
data class CardEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") val id: Int = 0,
    @ColumnInfo("card_name") val cardName: String,
    @ColumnInfo("name_on_card") val nameOnCard: String,
    @ColumnInfo("card_number") val cardNumberEncrypted: String,
    @ColumnInfo("exp_date") val expDate: String,
    @ColumnInfo("cvv") val cvvEncrypted: String,
)