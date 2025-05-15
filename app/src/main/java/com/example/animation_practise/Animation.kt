package com.example.animation_practise

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.coroutineScope
import kotlin.math.max
import kotlin.math.min

data class QuoteCard(
    val color: Color,
    val quoteIndex: Int,
    val textcolor : Color,
)

@Preview(showSystemUi = true)
@Composable
fun CardSlider(modifier: Modifier = Modifier) {
//    Quotes Index
    var currentQuoteIndex by remember { mutableIntStateOf(4) }

//    list of Cards
    var cards by remember {
        mutableStateOf(
            listOf(
                QuoteCard(
                    color = Color(0xFFBFD7ED),  // soft blue
                    quoteIndex = 0,
                    textcolor = Color(0xFF0A2540)  // navy
                ),
                QuoteCard(
                    color = Color(0xFFFFE5B4),  // peach beige
                    quoteIndex = 1,
                    textcolor = Color(0xFF442C2E)  // rich brown
                ),
                QuoteCard(
                    color = Color(0xFFD3E4CD),  // sage green
                    quoteIndex = 2,
                    textcolor = Color(0xFF1B4332)  // dark green
                ),
                QuoteCard(
                    color = Color(0xFFFFC8DD),  // blush pink
                    quoteIndex = 3,
                    textcolor = Color(0xFF5C374C)  // plum
                )
            )
        )
    }


//    Top Card Rotation Offset
    var offsetx by remember {
        mutableFloatStateOf(0f)
    }

    val quotes = listOf(
        mapOf("quote" to "Success is going from failure to failure without losing your enthusiasm.", "author" to "Winston Churchill"),
        mapOf("quote" to "Creativity is intelligence having fun.", "author" to "Albert Einstein"),
        mapOf("quote" to "The best way to predict the future is to invent it.", "author" to "Alan Kay"),
        mapOf("quote" to "Why fit in when you were born to stand out?", "author" to "Dr. Seuss"),
        mapOf("quote" to "If opportunity doesn’t knock, build a door.", "author" to "Milton Berle"),
        mapOf("quote" to "Do or do not. There is no try.", "author" to "Yoda"),
        mapOf("quote" to "Make it simple, but significant.", "author" to "Don Draper"),
        mapOf("quote" to "Don’t watch the clock; do what it does. Keep going.", "author" to "Sam Levenson"),
        mapOf("quote" to "Normal is an illusion. What is normal for the spider is chaos for the fly.", "author" to "Morticia Addams"),
        mapOf("quote" to "Be so good they can't ignore you.", "author" to "Steve Martin"),
        mapOf("quote" to "It always seems impossible until it's done.", "author" to "Nelson Mandela"),
        mapOf("quote" to "If you’re going through hell, keep going.", "author" to "Winston Churchill"),
        mapOf("quote" to "Don’t raise your voice. Improve your argument.", "author" to "Desmond Tutu"),
        mapOf("quote" to "Do one thing every day that scares you.", "author" to "Eleanor Roosevelt"),
        mapOf("quote" to "Don’t count the days, make the days count.", "author" to "Muhammad Ali"),
        mapOf("quote" to "The future belongs to those who believe in the beauty of their dreams.", "author" to "Eleanor Roosevelt"),
        mapOf("quote" to "You can't use up creativity. The more you use, the more you have.", "author" to "Maya Angelou"),
        mapOf("quote" to "I am not a product of my circumstances. I am a product of my decisions.", "author" to "Stephen Covey"),
        mapOf("quote" to "The only limit to our realization of tomorrow is our doubts of today.", "author" to "Franklin D. Roosevelt"),
        mapOf("quote" to "Don't be pushed by your problems. Be led by your dreams.", "author" to "Ralph Waldo Emerson"),
        mapOf("quote" to "Turn your wounds into wisdom.", "author" to "Oprah Winfrey"),
        mapOf("quote" to "Stay hungry, stay foolish.", "author" to "Steve Jobs"),
        mapOf("quote" to "There is no traffic jam along the extra mile.", "author" to "Roger Staubach"),
        mapOf("quote" to "You miss 100% of the shots you don't take.", "author" to "Wayne Gretzky"),
        mapOf("quote" to "Simplicity is the ultimate sophistication.", "author" to "Leonardo da Vinci"),
        mapOf("quote" to "In the middle of every difficulty lies opportunity.", "author" to "Albert Einstein"),
        mapOf("quote" to "Comparison is the thief of joy.", "author" to "Theodore Roosevelt"),
        mapOf("quote" to "Start where you are. Use what you have. Do what you can.", "author" to "Arthur Ashe"),
        mapOf("quote" to "If you want to lift yourself up, lift up someone else.", "author" to "Booker T. Washington"),
        mapOf("quote" to "Everything is figureoutable.", "author" to "Marie Forleo")
    )


    Box(
        modifier = Modifier.fillMaxSize(1f).padding(bottom = 50.dp),
        contentAlignment = Alignment.Center
    ){

        val swipeamount = 300f  // swipeThreshold of each card
        val rotate = 15f        // rotation of each card
        val offsetxx = 45f      // offsetx of each card
        val offsetyy = 10f      // offsety of each card


        cards.forEachIndexed { index, card->
            val istop = index == 0
            val cardmofifier = Modifier.width(250.dp)
                .height(160.dp)
                .padding(horizontal = 10.dp)
                .graphicsLayer{
                    rotationZ = index * rotate
                    translationX =   index * -offsetxx
                    translationY = index * offsetyy

                    if(istop){
                        transformOrigin = TransformOrigin(0.125f,0.125f)
                        rotationZ = -offsetx /20

                    }

                }
                .zIndex((cards.size - index).toFloat())
                .then(
                    if (istop) Modifier.pointerInput(Unit){
                        detectDragGestures(
                            onDrag = { _, amount ->
                                offsetx += amount.x
                            },
                            onDragEnd = {
                                if(offsetx > swipeamount || offsetx <-swipeamount){
                                    val newcard = cards.get(0).copy(quoteIndex = currentQuoteIndex % quotes.size)
                                    currentQuoteIndex ++


                                    cards = cards.drop(1) + newcard
                                }
                                offsetx = 0f
                            }
                        )

                    }
                    else Modifier
                )

            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = cardmofifier,
                colors = CardDefaults.cardColors(
                    containerColor = card.color
                )

            ){

                Box(
                    modifier = Modifier.fillMaxSize(1f).padding(10.dp)
                ) {
                        val quoteText =  quotes[card.quoteIndex]["quote"] ?: ""
                        val author =  quotes[card.quoteIndex]["author"] ?: ""

                    Box(
                        modifier = Modifier.size(20.dp).offset(
                            x= 10.dp,
                            y = 10.dp
                        )
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.Black)
                    )

                    Text(text = quoteText,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif,
                        color = card.textcolor,
                        modifier = Modifier.offset(y = 35.dp))

                    Text(text = "---"+author+"---",
                        fontSize = 12.sp,
                        color = card.textcolor,
                        modifier = Modifier.align(Alignment.BottomEnd))

                }
            }
        }
    }
}