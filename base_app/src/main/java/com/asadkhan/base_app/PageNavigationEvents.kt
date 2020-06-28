package com.asadkhan.base_app

sealed class PageNavigationEvent

class SavedCitiesPage() : PageNavigationEvent()
class SearchCitiesPage() : PageNavigationEvent()
class CityDetailsPage(val geoNameID: Int) : PageNavigationEvent()