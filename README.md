# **Mercado Libre Challenge**
## Arquitectura utilizada:

Escogí una arquitectura modularizada de manera híbrida, esto quiere decir que primero se modulariza por feature y después por layer, cabe aclarar que para un proyecto real de este tamaño seguramente hubiera escogido una arquitectura monolítica.


<img width="276" alt="image" src="https://user-images.githubusercontent.com/46971682/205694717-5e3f0b57-d242-4c50-9013-fcd8888b0b69.png">
Esta arquitectura, que se caracteriza por tener una alta granularidad, me permite lograr faster Gradle building time (especialmente a medida que el proyecto escala), mejor separación de concerns y reusabilidad de los módulos (librerías), además, se prohíbe la comunicación entre módulos a menos que exista una dependencia explícita, haciendo que cada módulo sea independiente y forzando un bajo acoplamiento.

Para este proyecto el domain layer funciona como dependencia principal tanto de las capas de présentation como de data.

### Gradle
Para los build scripts estoy usando Kotlin DSL, esto sumado a compose me permiten seguir una misma sintaxis (kotlin) a lo largo de todo el proyecto.
Adicionalmente estoy usando versionCatalogs para el manejo de dependencias, como es recomendado en la documentacion de gradle.

La logica principal se encuentra en el modulo `build-logic`, en donde se crearon diferentes custom gradle plugins para ayudar en la declaracion de los build.gradle de cada modulo

## Patrón de présentation: 
MVVM clean, organizado de la siguiente manera: 

* **Presentation**: Compose screens o views, viewmodels y cualquier otro stateholder.
* **Domain**: Use cases, domain models y abstracciones de los repositorios para que la comunicación entre layers se de por medio de abstracciones (siguiendo dependency inversion principle)

* **Data**: Diferentes data sources (local, remote, etc), entities (DTOs y ORMs), mappers, Retrofit service, Room DB o cualquier otro storage solution, etc.

## Inyección de dependencias:

Para este proyecto estoy usando Hilt como framework de DI.

El módulo (gradle) de cada feature contiene la lógica de inyección de dependencias de ese feature, sin embargo, hay un módulo (Hilt) principal en “app”

## Navegación: 

Utilice navigation component ya que provee soporte para compose y me permite crear un nav graph programáticamente:
```kotlin
fun NavGraphBuilder.meliNavGraph(
    onItemSelected: (String, NavBackStackEntry) -> Unit,
    upPress: () -> Unit
) {
    composable(route = Routes.Search.route) { from ->
        SearchScreen(onItemClick = { itemId -> onItemSelected(itemId, from) })
    }
    composable(
        route = "${Routes.ItemDetail.route}/{${Routes.ITEM_DETAIL_ARGUMENT}}",
        arguments = listOf(navArgument(Routes.ITEM_DETAIL_ARGUMENT) {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val itemId = arguments.getString(Routes.ITEM_DETAIL_ARGUMENT)
        ItemDetailScreen(itemId = itemId, upPress = upPress)
    }
}
```
## Testing:
Implementacion de test unitarios con `Junit`, `Mockk`, `MockWebServer`, `Truth`, etc. 

## Funcionalidad:
* #### Pantalla principal que hace las veces de un "feed screen", muestra todas las categorias disponibles en el site:

<img width="400" alt="image" src="https://user-images.githubusercontent.com/46971682/205698978-488d8973-1400-454c-be05-0a415f56feca.png">

* #### Pantalla de "suggestions" que muestra el historial de busqueda:

<img width="400" alt="image" src="https://user-images.githubusercontent.com/46971682/205699701-185a3498-6d50-494f-af21-b6b59177d614.png">

La data del historial de busqueda persiste por medio de datastore preferences.

* #### Pantalla de busqueda en tiempo real (implementada con un debounce de 1s y paginacion):

<img width="400" alt="image" src="https://user-images.githubusercontent.com/46971682/205699234-b1e71eac-d0f5-4266-ac92-67640404b53b.png">

* #### Pantalla del detalle de un item:

![item_detail_gif](https://user-images.githubusercontent.com/46971682/205701095-07680dcb-e122-41ed-926a-5af7e3780998.gif)

