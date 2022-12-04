package com.pedraza.sebastian.search_presentation.itemDetail

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.util.lerp
import com.pedraza.sebastian.android_helpers.components.MeliDivider
import com.pedraza.sebastian.core.R
import com.pedraza.sebastian.core.dimensions.LocalSpacing
import com.pedraza.sebastian.core.utils.formatPriceWithCurrency
import com.pedraza.sebastian.search_domain.models.item.Item
import com.pedraza.sebastian.search_presentation.components.ItemImage
import kotlin.math.max
import kotlin.math.min

private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = ImageOverlap + MinTitleOffset + GradientScroll
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)

@Composable
fun ItemDetailContent(
    item: Item,
    upPress: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        val scroll = rememberScrollState(0)
        Header()
        ItemDetailBody(item = item, scroll = scroll)
        ItemDetailTitle(item = item) { scroll.value }
        if (!item.pictures.isNullOrEmpty()) {
            Image(imageUrl = item.pictures!!.first()) { scroll.value }
        }
        Up(upPress)
    }
}

@Composable
private fun Header() {
    Spacer(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.primary
            )
    )
}

@Composable
private fun Up(upPress: () -> Unit) {
    IconButton(
        onClick = upPress,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
            tint = MaterialTheme.colors.onSurface,
            contentDescription = null
        )
    }
}

@Composable
fun ItemDetailBodySection(@StringRes sectionHeader: Int, sectionContent: String) {
    Column() {
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(sectionHeader),
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colors.onSurface,
            modifier = HzPadding,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = sectionContent,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = HzPadding
        )
    }
}

@Composable
private fun ItemDetailBody(
    item: Item,
    scroll: ScrollState
) {
    val spacing = LocalSpacing.current
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(MinTitleOffset)
        )
        Column(
            modifier = Modifier.verticalScroll(scroll)
        ) {
            Spacer(Modifier.height(GradientScroll))
            Surface(Modifier.fillMaxWidth()) {
                Column {
                    Spacer(Modifier.height(ImageOverlap))
                    Spacer(Modifier.height(TitleHeight + spacing.mercadoLibreSpacing22dp))
                    Text(
                        text = stringResource(R.string.description),
                        style = MaterialTheme.typography.h5.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colors.onSurface,
                        modifier = HzPadding
                    )
                    Spacer(Modifier.height(16.dp))
                    var seeMore by remember { mutableStateOf(true) }
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface,
                        maxLines = if (seeMore) 5 else Int.MAX_VALUE,
                        overflow = TextOverflow.Ellipsis,
                        modifier = HzPadding
                    )
                    val textButton = if (seeMore) {
                        stringResource(id = R.string.see_more)
                    } else {
                        stringResource(id = R.string.see_less)
                    }
                    Text(
                        text = textButton,
                        style = MaterialTheme.typography.button,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier
                            .heightIn(20.dp)
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                            .clickable {
                                seeMore = !seeMore
                            }
                    )
                    if (item.availableQuantity != null) {
                        ItemDetailBodySection(
                            sectionHeader = R.string.stock, sectionContent = stringResource(
                                id = R.string.stock_quantity,
                                item.availableQuantity.toString()
                            )
                        )
                    }
                    if (item.soldQuantity != null) {
                        ItemDetailBodySection(
                            sectionHeader = R.string.sold_quantity, sectionContent = stringResource(
                                id = R.string.stock_quantity,
                                item.soldQuantity.toString()
                            )
                        )
                    }
                    if (item.itemCondition.isNotEmpty()) {
                        ItemDetailBodySection(
                            sectionHeader = R.string.item_condition,
                            sectionContent = item.itemCondition
                        )
                    }
                    Spacer(Modifier.height(40.dp))
                    MeliDivider()
                    Spacer(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .height(spacing.mercadoLibreSpacing8dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ItemDetailTitle(item: Item, scrollProvider: () -> Int) {
    val spacing = LocalSpacing.current
    val maxOffset = with(LocalDensity.current) { MaxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            .statusBarsPadding()
            .offset {
                val scroll = scrollProvider()
                val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
                IntOffset(x = 0, y = offset.toInt())
            }
            .background(color = Color.White)
    ) {
        Spacer(Modifier.height(spacing.mercadoLibreSpacing16dp))
        Row() {
            Text(
                text = item.title,
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colors.onSurface,
                modifier = HzPadding
            )
        }
        Spacer(Modifier.height(spacing.mercadoLibreSpacing16dp))
        Text(
            text = formatPriceWithCurrency(item.price),
            style = MaterialTheme.typography.h6.copy(
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.colors.onSurface,
            modifier = HzPadding
        )
        Spacer(Modifier.height(spacing.mercadoLibreSpacing16dp))
        MeliDivider()
    }
}

@Composable
private fun Image(
    imageUrl: String,
    scrollProvider: () -> Int
) {
    val spacing = LocalSpacing.current
    val collapseRange = with(LocalDensity.current) { (MaxTitleOffset - MinTitleOffset).toPx() }
    val collapseFractionProvider = {
        (scrollProvider() / collapseRange).coerceIn(0f, 1f)
    }

    CollapsingImageLayout(
        collapseFractionProvider = collapseFractionProvider,
        modifier = HzPadding.then(Modifier.statusBarsPadding())
    ) {
        ItemImage(
            imageUrl = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            roundedCornerSize = spacing.mercadoLibreSpacing100dp
        )
    }
}

@Composable
private fun CollapsingImageLayout(
    collapseFractionProvider: () -> Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 1)

        val collapseFraction = collapseFractionProvider()

        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))

        val imageY = lerp(
            MinTitleOffset,
            MinImageOffset, collapseFraction
        ).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth) / 2, // centered when expanded
            constraints.maxWidth - imageWidth, // right aligned when collapsed
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = imageY + imageWidth
        ) {
            imagePlaceable.placeRelative(imageX, imageY)
        }
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
private fun SnackDetailPreview() {
    val itemMock = Item(
        title = "Microsoft Xbox Series S",
        description = "Con tu consola Xbox Series tendrás entretenimiento asegurado todos los días. Su tecnología fue creada para poner nuevos retos tanto a jugadores principiantes como expertos. \\n\\nLa nueva generación de consolas está comandada por la Xbox Series que llegó al mercado para sorprender a todos. Su potencia y alto rendimiento te permitirá reducir las horas de descarga de juegos y contenido de manera considerable en comparación con otras consolas. Además, vas a poder jugar durante horas mientras te diviertes con jugadores de todo el mundo.\\n\\nAdaptada a tus necesidades\\nGuarda tus apps, fotos, videos y mucho más"
    )
    ItemDetailContent(
        item = itemMock,
        upPress = { }
    )
}