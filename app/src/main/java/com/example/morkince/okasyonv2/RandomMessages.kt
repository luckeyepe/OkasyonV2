package com.example.morkince.okasyonv2

class RandomMessages {
    private var messages = ArrayList<String>()

    private fun loadMessages(){
        messages.addAll(listOf<String>(
            "Please wait, Looking for best lechon? visit Paray's Lechon ",
            "Please wait, Looking for best maker of uniforms and jerseys? visit Janine's Botique ",
            "Please wait, Looking for best maker of gowns for your wedding? visit Idea events and designs ",
//            "Please wait, Don't forget about flowers",
//            "Please wait, You look nice today",
//            "Please wait, Have a nice day",
//            "Please wait, Remember to call your parents",
//            "Please wait, It's such a beautiful day",
//            "Please wait, Smile more",
//            "Please wait, You're a good person",
//            "Please wait, Always thank God for everything",
//            "Please wait, You're Amazing Person",
//            "Please wait, Take it easy from time to time",
            "Please wait, Did you get a new hair cut? If not, visit David's Salon",
//            "Please wait, Dogs can't digest chocolate",
            "Please wait, Make sure to try the dresses you want before buying them",
            "Please wait, Don't have a bachelor party with your bride's father? visit Danny Booc",
            "Please wait, Peanut butter pairs well with chocolate",
            "Please wait, Don't get a haircut a day before the party",
            "Please wait, Remember to check you budget"))
    }

    fun getRandomMessage():String{
        loadMessages()
        val randomNumber = (0 until messages.size).random()

        return messages[randomNumber]
    }

}