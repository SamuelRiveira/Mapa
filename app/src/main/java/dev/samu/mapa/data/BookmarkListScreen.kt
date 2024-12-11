package dev.samu.mapa.data

import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.utsman.osmandcompose.DefaultMapProperties
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.ZoomButtonVisibility
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberMarkerState
import dev.samu.mapa.viewmodel.BookmarkViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import dev.samu.mapa.R

@Composable
fun BookmarkListScreen(
    viewModel: BookmarkViewModel
) {
    val bookmarks by viewModel.bookmarks.collectAsState()
    val bookmarkstype by viewModel.bookmarkstype.collectAsState()

    MyMapView(bookmarks, bookmarkstype)
}

@Composable
fun MyMapView(
    bookmarks: List<Bookmark>,
    bookmarkType: List<BookmarkType>,
    modifier: Modifier = Modifier
) {

    val cameraState = rememberCameraState {
        geoPoint = if (bookmarks.isNotEmpty()) {
            GeoPoint(bookmarks[0].coordinatesX, bookmarks[0].coordinatesY)
        } else {
            GeoPoint(28.957627225693827, -13.553854217297525)
        }
        zoom = 17.0
    }

    var mapProperties by remember {
        mutableStateOf(DefaultMapProperties)
    }

    SideEffect {
        mapProperties = mapProperties
            .copy(tileSources = TileSourceFactory.MAPNIK)
            .copy(isEnableRotationGesture = true)
            .copy(zoomButtonVisibility = ZoomButtonVisibility.NEVER)
    }

    OpenStreetMap(
        modifier = modifier.fillMaxSize(),
        cameraState = cameraState,
        properties = mapProperties
    ) {
        bookmarks.forEach { bookmark ->
            val context = LocalContext.current

            var icono by remember { mutableStateOf<Drawable?>(null) }

            when (bookmark.typeId) {
                1 -> icono = ContextCompat.getDrawable(context, R.drawable.restaurante)
                2 -> icono = ContextCompat.getDrawable(context, R.drawable.hotel)
                3 -> icono = ContextCompat.getDrawable(context, R.drawable.museo)
                4 -> icono = ContextCompat.getDrawable(context, R.drawable.farmacia)
            }

            val markerState = rememberMarkerState(
                geoPoint = GeoPoint(bookmark.coordinatesX, bookmark.coordinatesY)
            )

            Marker(
                state = markerState,
                title = bookmark.title,
                snippet = "ID Tipo: ${bookmark.typeId}",
                icon = icono
            ) {
                Column(
                    modifier = Modifier
                        .size(120.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(7.dp)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = it.title)
                    Text(text = it.snippet, fontSize = 10.sp)
                }
            }
        }
    }
}
