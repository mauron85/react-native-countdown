var React = require('react');
var ReactNative = require('react-native');
var PropTypes = require('prop-types');
var {
  requireNativeComponent,
  UIManager,
  ViewPropTypes
} = ReactNative;

class CountDownView extends React.Component {
  constructor(props) {
    super(props);
    this.onChange = this.onChange.bind(this);
  }

  componentWillUnmount() {
    this.stop();
  }

  start() {
    UIManager.dispatchViewManagerCommand(
      ReactNative.findNodeHandle(this),
      UIManager.CountDownView.Commands.start, []
    );
  }

  stop() {
    UIManager.dispatchViewManagerCommand(
      ReactNative.findNodeHandle(this),
      UIManager.CountDownView.Commands.stop, []
    );
  }

  onChange(event) {
    var {
      message,
      payload
    } = event.nativeEvent;
    if (message === 'finish') {
      typeof this.props.onFinish === 'function' && this.props.onFinish();
    } else if (message === 'tick') {
      typeof this.props.onTick === 'function' && this.props.onTick(payload);
    }
  }

  render() {
    return <RCTCountDownView { ...this.props
    }
    onChange = {
      this.onChange
    }
    />;
  }
}

CountDownView.propTypes = {
  millisInFuture: PropTypes.number,
  secondColor: PropTypes.string,
  secondColorDim: PropTypes.string,
  minuteColor: PropTypes.string,
  minuteColorDim: PropTypes.string,
  textColor: PropTypes.string,
  textSize: PropTypes.number,
  intervalMillis: PropTypes.number,
  onFinish: PropTypes.func,
  onTick: PropTypes.func,
  ...ViewPropTypes,
};

var RCTCountDownView = requireNativeComponent('CountDownView', CountDownView, {
  nativeOnly: {
    onChange: true
  }
});

module.exports = CountDownView;