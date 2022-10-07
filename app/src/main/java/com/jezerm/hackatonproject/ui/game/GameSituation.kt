package com.jezerm.hackatonproject.ui.game

import android.os.Parcel
import android.os.Parcelable

enum class CorrectSide {
    LEFT,
    RIGHT
}

class GameSituation(
    var situation: String,
    var leftMessage: String,
    var rightMessage: String,
    var correctAnswer: CorrectSide,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt() as CorrectSide
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(situation)
        parcel.writeString(leftMessage)
        parcel.writeString(rightMessage)
        parcel.writeInt(correctAnswer.ordinal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameSituation> {
        override fun createFromParcel(parcel: Parcel): GameSituation {
            return GameSituation(parcel)
        }

        override fun newArray(size: Int): Array<GameSituation?> {
            return arrayOfNulls(size)
        }
    }
}