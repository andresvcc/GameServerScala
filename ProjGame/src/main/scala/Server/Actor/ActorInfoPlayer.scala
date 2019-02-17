/*
este modelo de actor, es para aportar al usuario todas las informaciones necesarias sobre su cuenta
los datos de sus personajes y avances en el juego. no esta previsto para actualizar la posicion del
jugador, aunque almacena la ultima posicion del jugador si este estaba conectado y perdio su conexion.
*/

package Server.Actor

import Server.Command
import akka.actor.Actor

class ActorInfoPlayer extends Actor { //para usar las clase de mensajes Connecting y Disconnect
  import Command._
  import msgLogin._
  def receive:Receive = {
    // en el caso de recibir una solisitud de conexion
    case InfoPlayer(name) => // buscar la informacion del jugador y enviarsela
      sendMessageByName(name,"your info Player <"+name+">  0000000000\n")

    case InfoPlayerError(senderName,textError) =>
      println("enviando mensaje de error")
      sendMessageBySender(senderName,textError)

    case InfoServerStatus(name) =>
      sendMessageByName(name,"your info Server status 0000000000 \n")

    case InfoServerConnexion(name) =>
      sendMessageByName(name, "your info server <"+name+"> connexion info Host/ip connexion for player \n")

    case _ => println("InfoPlayer huh type command?"+receive)
  }
}
