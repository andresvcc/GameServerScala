package Server

import akka.actor.ActorRef

class PlayerTempConnect(aName:String, aSenderName:String, aHost:String, aActorRef:ActorRef){
  private val accountName:String = aName
  private val tempSenderName:String = aSenderName
  private val tempHostConnection:String = aHost
  //private val temporaryConnectionKey:TempKey = generateKey()
  val actorRef:ActorRef = aActorRef

  def name:String = accountName
  def senderName:String = tempSenderName
  def host:String = tempHostConnection
 }