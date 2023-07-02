package com.example.myapplication

class User {
    var name: String?=null
    var email: String?=null
    var uid: String?=null
    var password: String?=null
    var descrizione: String?=""
    var immagine: String?=""

    constructor(){}

    constructor(name:String?, email:String?, uid:String?,password:String?){
        this.name=name
        this.uid=uid
        this.email=email
        this.password=password
    }


}