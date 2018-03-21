var React = require('react');
var PropTypes = require('prop-types');
var { requireNativeComponent, NativeModules, DeviceEventEmitter, ViewPropTypes } = require('react-native');
var CountDown = NativeModules.CountDown;

class CountDownView extends React.Component {
  constructor(props) {
    super(props);
    this.onChange = this.onChange.bind(this);
  }

  componentWillUnmount() {
    CountDown.stop();
  }

  start() {
    CountDown.start();
  }

  stop() {
    CountDown.stop();
  }

  onChange(event) {
    if (event.nativeEvent.message === 'finish') {
      if (this.props.onFinish) {
        this.props.onFinish();
      }
    }
  }

  render() {
    return <RCTCountDownView {...this.props} onChange={this.onChange} />;
  }
}

CountDownView.propTypes = {
  millisInFuture: PropTypes.number,
  secondColor: PropTypes.string,
  secondColorDim: PropTypes.string,
  minuteColor: PropTypes.string,
  minuteColorDim: PropTypes.string,
  intervalMillis: PropTypes.number,
  onFinish: PropTypes.func,
  ...ViewPropTypes,
};

var RCTCountDownView = requireNativeComponent('CountDownView', CountDownView, {
  nativeOnly: { onChange: true }
});

module.exports = CountDownView;
