package com.example.tent6

class Constants {
    companion object{

        const val endpoint = "waldsensor"
        //const val primaryURL = "https://getpantry.cloud/apiv1/pantry/76702abf-cc5d-4084-a611-be8ec68ee3af/basket/"
        const val primaryURL = "https://getpantry.cloud/apiv1/pantry/1eea40ce-2ec9-4593-a2e6-5ad1a0484f31/basket/"
        const val states_file = "measure_states.txt"
        const val offline_counts_file = "offline_counts.txt"

        const val amountEntries = 30
        const val defaultState = "KEIN ZUSTAND"

        const val messageKey = "nachricht" //""noMessage"
        const val maxGetAttempts = 7 // eine Woche

        const val newlineCode = "NEWLINE"
    }
}