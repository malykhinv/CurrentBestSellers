package com.malykhinv.currentbestsellers.domain.model

import com.malykhinv.currentbestsellers.domain.model.categories.CategoryDetails

class CategorySorter {

    fun sort(listOfDetails: List<CategoryDetails>): Map<String, List<CategoryDetails>> {

        val mapOfCategories: MutableMap<String, List<CategoryDetails>> = mutableMapOf()

        val categories = enumValues<CATEGORIES>()

        categories.forEach { category ->

            val listOfDetailsByKeyword: MutableList<CategoryDetails> = mutableListOf()

            category.keywords.forEach { keyword ->

                for (details in listOfDetails) {
                    if (details.displayName.lowercase().contains(keyword)) {

                        if (category.stopWords.isNotEmpty()) {

                            category.stopWords.forEach { stopWord ->
                                if (!details.displayName.lowercase().contains(stopWord)) {
                                    listOfDetailsByKeyword.add(details)
                                }
                            }
                        } else {
                            listOfDetailsByKeyword.add(details)
                        }
                    }
                }

            }

            mapOfCategories[category.title] = listOfDetailsByKeyword
        }

        return mapOfCategories
    }

    enum class CATEGORIES(
        val title: String,
        val keywords : List<String>,
        val stopWords: List<String>) {

        FICTION(
            "Fiction",
            listOf("fiction"),
            listOf("nonfiction")),

        NONFICTION(
            "Nonfiction",
            listOf("nonfiction"),
            listOf()),

        GRAPHICS(
            "Graphics",
            listOf("manga", "graphic", "picture"),
            listOf()),

        CHILDREN(
            "Children",
            listOf("children"),
            listOf()),

        YOUNG_ADULT(
            "Young adult",
            listOf("middle", "young"),
            listOf()),

        AUDIO(
            "Audio",
            listOf("audio"),
            listOf()),

        MISC(
            "Miscellaneous",
            listOf(),
            listOf(
                FICTION.keywords.toString(),
                NONFICTION.keywords.toString(),
                GRAPHICS.keywords.toString(),
                CHILDREN.keywords.toString(),
                YOUNG_ADULT.keywords.toString(),
                AUDIO.keywords.toString()))
    }
}