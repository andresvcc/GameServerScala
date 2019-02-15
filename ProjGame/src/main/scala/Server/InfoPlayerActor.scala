/*
este modelo de actor, es para aportar al usuario todas las informaciones necesarias sobre su cuenta
los datos de sus personajes y avances en el juego. no esta previsto para actualizar la posicion del
jugador, aunque almacena la ultima posicion del jugador si este estaba conectado y perdio su conexion.
*/

package Server

import akka.actor.Actor

class InfoPlayerActor extends Actor { //para usar las clase de mensajes Connecting y Disconnect
  import Comunicated._
  import Command._
  def receive:Receive = {
    // en el caso de recibir una solisitud de conexion
    case InfoPlayer(name) => // buscar la informacion del jugador y enviarsela
      sendMessageName(name,"your info 0000000000")
    case _ => println("huh type command?")
  }
}
