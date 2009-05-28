
IokeBot = Origin mimic ; module

IokeBot dispatch = method(
  servletContext log("dispatched!!")

  if(events wasParticipantAddedToWave("iokebot@appspot.com"),
    wavelet = events wavelet
    blip = wavelet appendBlip
    textView = blip document
    textView append("Hi from Ioke!!")
  )
)
