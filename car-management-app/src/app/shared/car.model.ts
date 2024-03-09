export class CarConfiguration {
  constructor(
      public id: number = NaN,
      public carClass: CarFeature = new CarFeature(0,FeatureTypes.CLASS,"",0),
      public carType: CarFeature = new CarFeature(0,FeatureTypes.TYPE,"",0),
      public carMotor: CarFeature = new CarFeature(0,FeatureTypes.MOTOR,"",0),
      public carColor: CarFeature = new CarFeature(0,FeatureTypes.COLOR,"",0),
      public carExtras: Array<CarFeature> = [],
      public price: number = 0,
    ) {}
}

export class CarFeatures {
  constructor(
    public carMotors: Array<CarFeature> = [],
    public carClasses: Array<CarFeature> = [],
    public carTypes: Array<CarFeature> = [],
    public carExtras: Array<CarFeature> = [],
    public carColors: Array<CarFeature> = []
    ) {}
}

export class CarFeature {
  constructor(
      public id: number = 0,
      public featureType: FeatureTypes = FeatureTypes.EXTRAS,
      public name: string = "",
      public price: number  = 0
    ) {}
}

export enum FeatureTypes {
  CLASS = "CLASS",
  TYPE = "TYPE",
  MOTOR = "MOTOR",
  COLOR = "COLOR",
  EXTRAS = "EXTRAS"
}
