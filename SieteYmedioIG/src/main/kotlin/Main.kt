import recursos.Carta
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay

@Composable
fun MainScreen() {
    var nombreJugador by remember { mutableStateOf("") }
    var cartasJugador by remember { mutableStateOf(emptyList<Carta>()) }
    var cartasBanca by remember { mutableStateOf(emptyList<Carta>()) }
    var juegoIniciado by remember { mutableStateOf(false) }
    var juegoTerminado by remember { mutableStateOf(false) }
    var juego by remember { mutableStateOf(SieteyMedia()) }
    var nombreIngresado by remember { mutableStateOf(false) }
    var iniciarTurnoBanca by remember { mutableStateOf(false) }
    var mensajeResultado by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!juegoIniciado) {
            Text(text = "Bienvenido a Siete y Medio", style = typography.h3)
            Text(text = "El clasico juego de cartas", style = typography.h4)
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                juegoIniciado = true
                juegoTerminado = false
            }) {
                Text("Iniciar Juego")
            }
        } else if (!nombreIngresado) {
            Text("Ingrese su nombre:", style = typography.h5)
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = nombreJugador,
                onValueChange = { nombreJugador = it },
                label = { Text("Nombre del Jugador") }
            )
            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                if (nombreJugador.isNotEmpty()) nombreIngresado = true
            }) {
                Text("Continuar")
            }
        } else {
            if (!juegoTerminado) {
                if (!iniciarTurnoBanca) {
                    Button(onClick = {
                        juego.recibirCartaJugador()
                        cartasJugador = juego.getCartasJugador()
                        if (juego.verificarFinDelJuego()) {
                            mensajeResultado = juego.obtenerResultado()
                            juegoTerminado = true
                        } else {
                            mensajeResultado = "Carta entregada a $nombreJugador"
                            iniciarTurnoBanca = true
                        }
                    }) {
                        Text("Juega $nombreJugador")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(onClick = {
                        juego.plantarse()
                        mensajeResultado = "Te has plantado"
                        iniciarTurnoBanca = true
                        if (juego.verificarFinDelJuego()) {
                            juegoTerminado = true
                        }
                    }) {
                        Text("Plantarse")
                    }
                } else {
                    LaunchedEffect(iniciarTurnoBanca) {
                        mensajeResultado = "La banca está pensando..."

                        delay(2000)

                        juego.jugarBancaUnaCarta()
                        cartasBanca = juego.getCartasBanca()

                        if (juego.verificarFinDelJuego()) {
                            mensajeResultado = juego.obtenerResultado()
                            juegoTerminado = true
                        } else {
                            mensajeResultado = "La banca ha tomado una carta"
                            iniciarTurnoBanca = false // Reset this variable
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                cartasJugador = emptyList()
                cartasBanca = emptyList()
                juegoIniciado = false
                juegoTerminado = false
                nombreIngresado = false
                iniciarTurnoBanca = false
                mensajeResultado = " "
                juego = SieteyMedia()
            }) {
                Text("Reiniciar Juego")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Jugador: $nombreJugador", style = typography.body1)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "$mensajeResultado", style = typography.body2)
            Spacer(modifier = Modifier.height(20.dp))

            MostrarCartas("$nombreJugador", cartasJugador)
        }
    }
}

@Composable
fun MostrarCartas(jugador: String, cartas: List<Carta>) {
    Column {
        Text(text = "Cartas de $jugador:", style = typography.body1)
        cartas.forEach { carta ->
            Text(text = carta.toString(), style = typography.body2)
        }
        Text(text = "Valor total: ${calcularValor(cartas)}", style = typography.body2)
    }
}

fun calcularValor(cartas: List<Carta>): Double {
    return cartas.sumOf { if (it.numero > 7) 0.5 else it.numero.toDouble() }
}

@Preview
@Composable
fun DefaultPreview() {
    MainScreen()
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MainScreen()
    }
}