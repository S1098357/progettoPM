package com.example.myapplication

import android.widget.TextView

class PropertyValue {

    var addressTextView: String? = null
    var priceTextView: String? = null
    var propertyTypeTextView:String? = null
    var roomsTextView: String?= null
    var user: String?=null
    var propertyCode: String?=null

    constructor(){}

    constructor(addressTextView: String?, priceTextView: String?, propertyTypeTextView:String?, roomsTextView:String?, user: String?,propertyCode: String?){
        this.addressTextView=addressTextView
        this.priceTextView=priceTextView
        this.roomsTextView=roomsTextView
        this.propertyTypeTextView=propertyTypeTextView
        this.user=user
        this.propertyCode=propertyCode
    }
}