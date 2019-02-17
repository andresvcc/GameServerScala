/*
este modelo de actor gestiona el login entre el usuario y el server
name: nombre de usuario
senderName: identificador de usuario
*/
package Server.Actor
import Server.Actor.ManagerActor.{bdPlayerActor, infoPlayerActor, infoServerActor, infoServerStatusActor}
import akka.actor.Actor

class ActorLogin extends Actor {

  import MsgBdPlayer._
  import msgLogin._
  def receive:Receive = {
    case LoginClient(name) =>
      infoServerActor ! InfoServerConnexion(name)
      infoServerStatusActor ! InfoServerStatus(name)
      infoPlayerActor ! InfoPlayer(name)
    case Connecting(name,senderName,host) =>
      bdPlayerActor ! AddPlayer(name, senderName, host)
    case Disconnect(senderName)=>
      bdPlayerActor ! SupPlayerBySender(senderName)
    case InfoPlayerError(senderName, textError)=>
      infoPlayerActor ! InfoPlayerError(senderName,textError)
    case _ => println("LoginClient huh type command? :"+receive)
  }
}