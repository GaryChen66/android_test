package co.metalab.tech.interview.data

import androidx.annotation.ColorRes
import co.metalab.tech.interview.R

data class Type(val id: Int, val identifier: String) {
    @ColorRes
    fun getColor(): Int = when (identifier) {
        "normal" -> R.color.colorNormal
        "fire" -> R.color.colorFire
        "water" -> R.color.colorWater
        "electric" -> R.color.colorElectric
        "grass" -> R.color.colorGrass
        "ice" -> R.color.colorIce
        "fighting" -> R.color.colorFighting
        "poison" -> R.color.colorPoison
        "ground" -> R.color.colorGround
        "flying" -> R.color.colorFlying
        "psychic" -> R.color.colorPsychic
        "bug" -> R.color.colorBug
        "rock" -> R.color.colorRock
        "ghost" -> R.color.colorGhost
        "dragon" -> R.color.colorDragon
        "dark" -> R.color.colorDark
        "steel" -> R.color.colorSteel
        "fairy" -> R.color.colorFairy
        else -> R.color.colorNormal
    }
}
