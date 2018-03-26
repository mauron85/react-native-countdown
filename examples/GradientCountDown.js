import React, { Component } from 'react';
import { StyleSheet, View, Button } from 'react-native';
import CountDownView from 'react-native-mauron85-countdown';

function rgbToHex([r, g, b]) {
  return '#' + ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1);
}

function hexToRgb(hex) {
  var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
  return result
    ? [
        parseInt(result[1], 16),
        parseInt(result[2], 16),
        parseInt(result[3], 16)
      ]
    : null;
}

// Returns a single rgb color interpolation between given rgb color
// based on the factor given; via https://codepen.io/njmcode/pen/axoyD?editors=0010
function interpolateColor(color1, color2, factor) {
  if (arguments.length < 3) {
    factor = 0.5;
  }
  var result = color1.slice();
  for (var i = 0; i < 3; i++) {
    result[i] = Math.round(result[i] + factor * (color2[i] - color1[i]));
  }
  return result;
}

// https://graphicdesign.stackexchange.com/questions/83866/generating-a-series-of-colors-between-two-colors
function interpolateColors(color1, color2, steps) {
  var stepFactor = 1 / (steps - 1),
    interpolatedColorArray = [];

  for (var i = 0; i < steps; i++) {
    interpolatedColorArray.push(
      rgbToHex(interpolateColor(color1, color2, stepFactor * i))
    );
  }

  return interpolatedColorArray;
}

export default class CountDown extends Component {
  constructor(props) {
    super(props);

    this.onPress = this.onPress.bind(this);
    this.onFinish = this.onFinish.bind(this);
    this.onTick = this.onTick.bind(this);

    this.gradients = [
      interpolateColors(hexToRgb('#ff1a00'), hexToRgb('#ef017c'), 60),
      interpolateColors(hexToRgb('#00bfff'), hexToRgb('#ef017c'), 60),
    ];

    const hours = 2;
    const minutes = 5;
    const seconds = 10;
    const millisInFuture = (hours * 60 * 60 + minutes * 60 + seconds) * 1000;
    const clock = this._getClock(millisInFuture);

    this.millisInFuture = millisInFuture
    this.state = {
      isStarted: false,
      secondColor: this._getGradientColor(clock[0], 0),
      minuteColor: this._getGradientColor(clock[1], 1),
      secondColorDim: '#aaaaaa',
      minuteColorDim: '#cccccc',
      textColor: '#ffffff'
    };
  }

  componentWillUnmount() {
    this._interval && clearInterval(this._interval);
    this._interval = null;
  }

  _getClock(millis) {
    const seconds = Math.floor(millis / 1000);
    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds - hours * 3600) / 60);
    return [seconds % 60, minutes, hours];
  }

  _getGradientColor(interval, gradientIndex) {
    const index = interval % this.gradients[gradientIndex].length;
    return this.gradients[gradientIndex][index];
  }

  onPress() {
    const { isStarted } = this.state;
    if (isStarted) {
      this.countDown.stop();
    } else {
      this.countDown.start();
    }
    this.setState({ isStarted: !isStarted });
  }

  onFinish() {
    this.setState({ isStarted: false });
  }

  onTick(millisUntilFinished) {
    const clock = this._getClock(millisUntilFinished);
    this.setState({
      secondColor: this._getGradientColor(clock[0], 0),
      minuteColor: this._getGradientColor(clock[1], 1)
    });
  }

  render() {
    const {
      secondColor,
      minuteColor,
      secondColorDim,
      minuteColorDim,
      textColor,
      isStarted
    } = this.state;
    return (
      <View style={styles.container}>
        <CountDownView
          style={styles.countdown}
          millisInFuture={this.millisInFuture}
          intervalMillis={1000}
          secondColor={secondColor}
          secondColorDim={secondColorDim}
          minuteColor={minuteColor}
          minuteColorDim={minuteColorDim}
          textColor={textColor}
          textSize={30}
          onFinish={this.onFinish}
          onTick={this.onTick}
          ref={component => {
            this.countDown = component;
          }}
        />
        <Button
          onPress={this.onPress}
          title={isStarted ? 'Stop' : 'Start'}
          color="#841584"
          accessibilityLabel="Starts countdown"
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#333336'
  },
  countdown: {
    width: 300,
    height: 300,
    marginBottom: 20
  }
});
