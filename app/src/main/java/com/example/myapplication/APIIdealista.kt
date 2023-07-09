package com.example.myapplication


//classe per contenere JSON idealista
data class Property(
    val elementList: List<Element>,
    val total: Int,
    val totalPages: Int,
    val actualPage: Int,
    val itemsPerPage: Int,
    val numPaginations: Int,
    val summary: List<String>,
    val alertName: String,
    val totalAppliedFilters: Int,
    val lowerRangePosition: Int,
    val upperRangePosition: Int,
    val paginable: Boolean
)

data class Element(
    val propertyCode: String,
    val thumbnail: String,
    val externalReference: String,
    val numPhotos: Int,
    val price: Double,
    val propertyType: String,
    val operation: String,
    val size: Double,
    val exterior: Boolean,
    val rooms: Int,
    val bathrooms: Int,
    val address: String,
    val province: String,
    val municipality: String,
    val district: String,
    val country: String,
    val neighborhood: String,
    val latitude: Double,
    val longitude: Double,
    val showAddress: Boolean,
    val url: String,
    val distance: String,
    val description: String,
    val hasVideo: Boolean,
    val status: String,
    val newDevelopment: Boolean,
    val parkingSpace: ParkingSpace,
    val priceByArea: Double,
    val detailedType: DetailedType,
    val suggestedTexts: SuggestedTexts,
    val hasPlan: Boolean,
    val has3DTour: Boolean,
    val has360: Boolean,
    val hasStaging: Boolean,
    val highlight: Highlight,
    val topNewDevelopment: Boolean,
    val superTopHighlight: Boolean
)

data class ParkingSpace(
    val hasParkingSpace: Boolean,
    val isParkingSpaceIncludedInPrice: Boolean
)

data class DetailedType(
    val typology: String,
    val subTypology: String
)

data class SuggestedTexts(
    val subtitle: String,
    val title: String
)

data class Highlight(
    val groupDescription: String
)
