package tournament.api.app

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration

class MyAppConfig(@JsonProperty("configTest") val configTest: String) : Configuration()
