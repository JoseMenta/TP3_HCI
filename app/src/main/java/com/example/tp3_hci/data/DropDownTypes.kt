package com.example.tp3_hci.data

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.tp3_hci.R
import com.example.tp3_hci.data.model.RoutineOverview
import com.example.tp3_hci.data.repository.OrderCriteria
import com.example.tp3_hci.data.repository.OrderDirection

// Formas de ordenar las rutinas
sealed class OrderByItem(
    @StringRes val stringId: Int,
    val criteria : OrderCriteria
){
    object Name: OrderByItem(
        stringId = R.string.name,
        criteria = OrderCriteria.Name
    )
    object CreationDate: OrderByItem(
        stringId = R.string.creation_date,
        criteria = OrderCriteria.CreationDate
    )
    object Rating: OrderByItem(
        stringId = R.string.rating,
        criteria = OrderCriteria.Score
    )
    object Difficulty: OrderByItem(
        stringId = R.string.difficulty,
        criteria = OrderCriteria.Difficulty
    )
    object Category: OrderByItem(
        stringId = R.string.category,
        criteria = OrderCriteria.Category
    )
}

// Formas de definir el sentido del orden
sealed class OrderTypeItem(
    @StringRes val stringIdAbrev: Int,
    @StringRes val stringId: Int,
    val orderDirection: OrderDirection
){
    object Descending: OrderTypeItem(
        stringId = R.string.descending,
        stringIdAbrev = R.string.descending_abrev,
        orderDirection = OrderDirection.Desc
    )
    object Ascending: OrderTypeItem(
        stringId = R.string.ascending,
        stringIdAbrev = R.string.ascending_abrev,
        orderDirection = OrderDirection.Asc
    )
}




// Formas para buscar una rutina
sealed class SearchByItem(
    @StringRes val stringId: Int
) {
    object RoutineName : SearchByItem(stringId = R.string.routine_name)
    object CreatorName : SearchByItem(stringId = R.string.creator_name)
}

// Parametriza los DropDownChips
open class DropDownItem(
    private val stringId: Int
){
    @Composable
    open fun getString(): String{
        return stringResource(id = this.stringId)
    }
}

// Dificultad de una rutina
sealed class DifficultyItem (
    val times: Int
): DropDownItem(
    R.string.difficulty_icon
) {
    object Expert : DifficultyItem(5)
    object Advanced : DifficultyItem(4)
    object Intermediate : DifficultyItem(3)
    object Beginner : DifficultyItem(2)
    object Rookie : DifficultyItem(1)

    @Composable
    override fun getString(): String{
        return super.getString().repeat(this.times)
    }

    override fun equals(other: Any?): Boolean {
        if(other !is DifficultyItem){
            return false
        }
        return this.times == other.times
    }

    override fun hashCode(): Int {
        return times
    }
}

// Valoraciones para una rutina
sealed class RatingItem(
    val times: Int
): DropDownItem(
    R.string.rating_icon
){
    object Five : RatingItem(5)
    object Four : RatingItem(4)
    object Three : RatingItem(3)
    object Two : RatingItem(2)
    object One : RatingItem(1)

    @Composable
    override fun getString(): String{
        return super.getString().repeat(this.times)
    }

    override fun equals(other: Any?): Boolean {
        if(other !is RatingItem){
            return false
        }
        return this.times == other.times
    }

    override fun hashCode(): Int {
        return times
    }
}

// Categorias para una rutina
sealed class CategoryItem(
    @StringRes val stringId: Int,
    val name : String
): DropDownItem(
    stringId
){
    object FullBody : CategoryItem(
        stringId = R.string.category_full_body,
        name = "Cuerpo completo"
    )
    object UpperBody : CategoryItem(
        stringId = R.string.category_upper,
        name = "Tren superior"
    )
    object MidBody : CategoryItem(
        stringId = R.string.category_mid,
        name = "Tren medio"
    )
    object LowerBody : CategoryItem(
        stringId = R.string.category_lower,
        name = "Tren inferior"
    )

    override fun equals(other: Any?): Boolean {
        if(other !is CategoryItem){
            return false
        }
        return this.stringId == other.stringId
    }

    override fun hashCode(): Int {
        return stringId
    }
}




