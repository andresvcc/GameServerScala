/*
este objeto pertenece a un conjunto de metodos del projecto GameServer en scala
contiene los metodos publica que usan los actores y tambien informaciones necesarias
para el funcionamiento de varias clases
*/

package Server

import akka.io.Tcp.Write
import akka.util.ByteString
import Server.BD.BdPlayerTempConnect
import Server.BD.BdClientActive

case object Command{
  //-----------------------------------------
  // caracter usado para iniciar cualquier comando, es solo para verificar.
  val CommandCharacter = "~"
  //-----------------------------------------

  //-----------------------------------------

  //***********************************************************************************
  //extrae el comando de una linea de string Ex: ~login andres -> return ~login.
  def getCommand(command:String):Array[String] = {
    command.split(" ")
  }

  //determina si una linea de string es un comando.
  def isCommand(message: String): Boolean = {
    message.startsWith(CommandCharacter)
  }

  //ayade los identificadores de un usuario a BdClient.
  def connectingUser(name:String, senderName:String, host:String): Boolean ={

    if(BdPlayerTempConnect.existSender(senderName)){
      sendMessageByName( name,"is not possible, your are connected!")
      false
    }else if (BdPlayerTempConnect.existName(name)) {
      sendMessageByName( name,"There is already an user with this username!")
      false
    } else {
      BdPlayerTempConnect.addPlayer(name,senderName,host)
      true
    }
  }

  //elimina los identificadores de un usuario de BdClient.
  def disconnectUser(senderName:String): Boolean ={
    if (BdPlayerTempConnect.existSender(senderName) || BdClientActive.exist(senderName)) {
      BdPlayerTempConnect.supPlayerBySender(senderName)
      BdClientActive.supActiveClient(senderName)
      true
    } else {
      println("ERROR: not user! "+senderName)
      false
    }
  }

  //envia un mensaje TCP/ip a un usuario.
  def sendMessageBySender(senderName: String, message: String, serverMessage: Boolean = false): Unit = {
    val actorRef = BdPlayerTempConnect.playerActorRefBySender(senderName)
    if(actorRef != null){
      if (serverMessage) {
        actorRef ! Write(ByteString("[SERVER]: " + message))
      } else {
        actorRef ! Write(ByteString(message))
      }
    }else{
      println("user no found "+senderName)
    }
  }

  def sendMessageByName(name: String, message: String, serverMessage: Boolean = false): Unit = {
    val actorRef = BdPlayerTempConnect.playerActorRefByName(name)
    if(actorRef != null){
      if (serverMessage) {
        actorRef ! Write(ByteString("[SERVER]: " + message))
      } else {
        actorRef ! Write(ByteString(message))
      }
    }else{
      println("user no found "+name)
    }
  }

  //envia un mensaje TCP/ip a todos los usuarios.
  def sendToAll(messageSender: String, message: String, serverMessage: Boolean = false): Unit = {
    if (serverMessage) {
      BdPlayerTempConnect.allActorRef().foreach(actorRef => actorRef ! Write(ByteString("[SERVER for ALL]: " + message)))
    } else {
      BdPlayerTempConnect.allActorRef().foreach(actorRef => actorRef ! Write(ByteString("<" + messageSender + ">: " + message)))
    }
  }
}

