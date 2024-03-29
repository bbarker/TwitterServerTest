package edu.cornell.comm.twitter.server

import com.twitter.finagle.{Http, Service}
import com.twitter.io.Charsets
import com.twitter.server.TwitterServer
import com.twitter.util.{Await, Future}
import org.jboss.netty.buffer.ChannelBuffers.copiedBuffer
import org.jboss.netty.handler.codec.http._

/**
 * Created by Brandon Elam Barker on 7/2/2015.
 */

object BasicServer extends TwitterServer {
  val service = new Service[HttpRequest, HttpResponse] {
    def apply(request: HttpRequest) = {
      val response =
        new DefaultHttpResponse(request.getProtocolVersion, HttpResponseStatus.OK)
      response.setContent(copiedBuffer("hello", Charsets.Utf8))
      Future.value(response)
    }
  }

  def main() {
    val server = Http.serve(":8888", service)
    onExit {
      server.close()
    }
    Await.ready(server)
  }
}