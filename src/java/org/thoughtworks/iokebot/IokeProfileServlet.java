package org.thoughtworks.iokebot;

import com.google.wave.api.ParticipantProfile;
import com.google.wave.api.ProfileServlet;

public class IokeProfileServlet extends ProfileServlet {
  @Override
  public String getRobotAvatarUrl() {
    return "http://ioke.org/img/IokeLogo.png";
  }

  @Override
  public String getRobotName() {
    return "Ioke the Robot";
  }
}
