
IokeBot = Origin mimic ; module

IokeBot dispatch = method(
  servletContext log("dispatched!!")

  servletContext log("events: #{events toString}")
  servletContext log("have I been added?: #{events wasParticipantAddedToWave("iokebot@appspot.com")}")

  if(events wasParticipantAddedToWave("iokebot@appspot.com"),
    wavelet = events wavelet
    blip = wavelet appendBlip
    textView = blip document
    textView append("\nYou have added an Ioke interpreter.\nUse ioke: to execute Ioke code.")
  )

  servletContext log("events events: #{events events toString}")
  events events select(type == com:google:wave:api:EventType field:BLIP_SUBMITTED) each(event,
    text = event blip document text
    if(#/ioke:({code}.*)$/s =~ text,
      newBlip = event blip createChild
      view = newBlip document
      bind(rescue(fn(c, view append("Had an error: #{escape(c report)}"))),
        view append(escape(Message fromText(it code) evaluateOn(self, self) inspect)))
    )
  )
)

IokeBot escape = method(input,
  input replaceAll("<", "&lt;") replaceAll(">", "&gt;")
)
