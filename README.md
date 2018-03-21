
# React Native CountDown

currently Android only

![Android screenshot](/screen.png "screenshot of plugin running on Android")


# Install

```
npm i react-native-mauron85-countdown --save
```

```
react-native link react-native-mauron85-countdown
```

Append to `android/app/build.gradle`:

```
repositories {
    flatDir {
        dirs "../../node_modules/react-native-mauron85-countdown/lib/libs"
    }
}

dependencies {
  ...
  compile(name: 'richpath-release', ext:'aar')
}
```

# Example

```
import React, { Component } from 'react';
import { StyleSheet, View, Button } from 'react-native';
import CountDownView from 'react-native-mauron85-countdown';

export default class CountDown extends Component {
  constructor(props) {
    super(props);
    this.onPress = this.onPress.bind(this);
    this.onFinish = this.onFinish.bind(this);
    this.state = { isStarted: false };
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

  render() {
    return (
      <View style={styles.container}>
        <CountDownView
          style={styles.countdown}
          millisInFuture={140000}
          intervalMillis={1000}
          secondColor="#f57df5"
          secondColorDim="#aaaaaa"
          minuteColor="#00bfff"
          minuteColorDim="#cccccc"
          onFinish={this.onFinish}
          ref={(component) => { this.countDown = component; }}
        />
        <Button
          onPress={this.onPress}
          title={this.state.isStarted ? 'Stop' : 'Start'}
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
    backgroundColor: '#333336',
  },
  countdown: {
    width: 300,
    height: 300,
    marginBottom: 20,
  }
});
```
