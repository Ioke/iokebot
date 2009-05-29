
IokeBot = Origin mimic ; module

IokeBot dispatch = method(
  servletContext log("dispatched!!")

  if(events wasParticipantAddedToWave("iokebot@appspot.com"),
    wavelet = events wavelet
    blip = wavelet appendBlip
    textView = blip document
    textView append("\nYou have added an Ioke interpreter.\nUse ioke: to execute Ioke code.")
  )

  evs = events events
  (0...(evs size asRational)) each(i,
    event = evs get(i)
    if(event type == com:google:wave:api:EventType field:BLIP_SUBMITTED,
      text = event blip document text
      if(#/ioke:({code}.*)\n/ =~ text,
        newBlip = event blip createChild
        view = newBlip document
        view append("\n; #{it code}\n")
        view append(Message doText(it code) inspect)
      )
    )
  )
)
