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
    if (event.nativeEvent.message === 'finish') {
      if (this.props.onFinish) {
        this.props.onFinish();
      }
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
  intervalMillis: PropTypes.number,
  onFinish: PropTypes.func,
  ...ViewPropTypes,
};

var RCTCountDownView = requireNativeComponent('CountDownView', CountDownView, {
  nativeOnly: {
    onChange: true
  }
});

module.exports = CountDownView;