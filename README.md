
<html>

<head>
<title>Flocking Applet</title>
</head>

<body bgcolor="#FFFFFF" text="#000080" link="#008000" vlink="#FFFF00">

<h2 align="center"><font face="Comic Sans MS" color="#8080FF">Math-254 Final Project</font></h2>

<h3 align="center">Flocking Applet, a Markov Chain Example</h3>

<p>&nbsp;</p>

<p align="center">
<applet CODE="MarkovChainApplet.class" WIDTH="450" HEIGHT="367"
ARCHIVE="MarkovChainApplet.jar,symbeans.jar,swingall.jar">
</applet>
</p>

<p>John Kavanaugh<br>
Brian Jackson</p>

<p><strong>Slider Information</strong>

<ul>
  <li><strong>Amount</strong> - These control the number of creatures of a species</li>
  <li><strong>Speed</strong> - These control the speed at which the creatures move (there is a
    random &quot;jitter&quot; that is not controlled by this slider)</li>
  <li><strong>Coherence</strong> - These control how much the creatures want to be near
    creatures of the same species ( left is indifferent, right is extreme)</li>
  <li><strong>Avoidance</strong> - These are the &quot;personal space&quot; factor of how much
    a creature must keep its distance for creatures of the same species ( left is indifferent,
    right is extreme)</li>
  <li><strong>Interaction</strong> - These control how a creature &quot;feels&quot; about
    creatures of a different species (left is flee/prey, right is fight/predator)</li>
</ul>

<hr>

<h3>Brief Overview</h3>

<p>Each creature takes into account three factors, coherence, avoidance and interaction.
&nbsp; Coherence is the factor of wanting to group with other creatures like itself.
&nbsp; Avoidance is the factor that each creature needs its own personal space relative to
creatures of the same species.&nbsp; Interaction is the factor that a creature may either
flee from creatures of a different species, run to creatures of a different species or a
range in between.&nbsp; In this applet each creature takes these factors into account for
every other creature in the &quot;world&quot; and averages these factors to find which
direction to go in and how far. This is done in every timeslice for every creature.
&nbsp; This &quot;individual&quot; thought that each creature gives based on where it
currently is, regardless of where it has been, can be shown to be a Markov Chain and will
also cause the creatures to exhibit flocking patterns.&nbsp; </p>

<hr>

<h3>How is this a Markov Chain?</h3>

<p>Each creature has different probabilities that it will move either toward, away from,
to the left or right of another creature.&nbsp; There are three matrices of probabilities
that each creature has.&nbsp; Below is show the initial matrices of our Red species in the
applet.</p>

<table border="0" width="100%">
  <tr>
    <td width="25%"><p align="center"><strong>Key</strong></td>
    <td width="25%"><p align="center"><strong>Cohesion</strong></td>
    <td width="25%"><p align="center"><strong>Avoidance</strong></td>
    <td width="25%"><p align="center"><strong>Interaction</strong></td>
  </tr>
  <tr>
    <td width="25%"><table border="1" width="100%">
      <tr>
        <td width="50%">Toward</td>
        <td width="50%">Away</td>
      </tr>
      <tr>
        <td width="50%">Left</td>
        <td width="50%">Right</td>
      </tr>
    </table>
    </td>
    <td width="25%"><table border="1" width="100%">
      <tr>
        <td width="50%">1.0</td>
        <td width="50%">0.0</td>
      </tr>
      <tr>
        <td width="50%">0.5</td>
        <td width="50%">0.5</td>
      </tr>
    </table>
    </td>
    <td width="25%"><table border="1" width="100%">
      <tr>
        <td width="50%">0.0</td>
        <td width="50%">1.0</td>
      </tr>
      <tr>
        <td width="50%">0.5</td>
        <td width="50%">0.5</td>
      </tr>
    </table>
    </td>
    <td width="25%"><table border="1" width="100%">
      <tr>
        <td width="50%">1.0</td>
        <td width="50%">0.0</td>
      </tr>
      <tr>
        <td width="50%">0.5</td>
        <td width="50%">0.5</td>
      </tr>
    </table>
    </td>
  </tr>
</table>

<p>You can see that the cohesion factor will always press the creature to go toward like
creatures with 50/50 chance of veering left or right of that creature. It is important to
understand that this left and right terms are relative to the creature moving and facing
directly at the other creature. The avoidance factor will always press the creature to go
away from the like creatures with a 50/50 chance of side motion.&nbsp; The red is the
&quot;antagonist&quot; or hunter/predator in the scenario so the interaction factor will
always press the creature to go toward creatures of different species with a 50/50 chance
of side motion again.</p>

<p>Now to actually calculate where this creature will go next we use the cohesion
probability and avoidance probability with a random number generator in the following
algorithm</p>

<blockquote>
  <p><em>coh_factor = get_direction(coh_prob); // toward = 1, away = -1<br>
  avd_factor = get_direction(avd_prob); // toward = 1, away = -1<br>
  dir = speed*(coh_factor*distance + avd_factor/distance^2);</em></p>
</blockquote>

<p>This will return dir which will be postive if the creature is to go toward the other
creature and negative if the creature is to head away from the other creature.&nbsp; The
size of dir will dictate how much to move in that direction.&nbsp; The same idea will be
used if the creature is of another species but this &quot;vector&quot; would be based on
interaction multiplied by distance.&nbsp; </p>

<p>All of these &quot;vectors&quot; are found compared to all other creatures in the world
and they are averaged together to find a final &quot;vector&quot; for which the creature
will move.</p>
</body>
</html>

