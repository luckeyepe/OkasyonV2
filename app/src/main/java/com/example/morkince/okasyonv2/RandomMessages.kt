package com.example.morkince.okasyonv2

class RandomMessages {
    private var messages = ArrayList<String>()

    private fun loadMessages(){
        messages.addAll(listOf<String>("Don't forget about flowers",
            "You look nice today",
            "Remember to call your parents",
            "It's such a beautiful day",
            "Smile more",
            "Take it easy from time to time",
            "Did you get a new hair cut?",
            "Dogs can't digest chocolate",
            "Make sure to try the dresses you want before buying them",
            "Don't have a bachelor party with your bride's father",
            "Peanut butter pairs well with chocolate",
            "Don't get a haircut a day before the party",
            "Remember to check you budget"))
    }

    fun getRandomMessage():String{
        loadMessages()
        val randomNumber = (0 until messages.size).random()

        return messages[randomNumber]
    }

}